package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContentTypeException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAuthController.class);

    @RequestMapping(path = "/register")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form) {
        LOGGER.info("Accessed /register GET controller");

        return new ModelAndView("views/register");
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, final HttpServletRequest request) {
        LOGGER.info("Accessed /register POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form RegisterForm data");
            return register(form);
        }


        if (!form.getPassword().equals(form.getConfirmPassword())) {
            //Global error, that's why it has "".
            LOGGER.warn("Error in form RegisterForm, passwords dont match");
            errors.rejectValue("", "validation.user.passwordsDontMatch");
            return register(form);
        }

        User user;
        final ModelAndView mav = new ModelAndView("redirect:/user/account");
        try {
            user = userService.createUser(form.getPassword(),
                form.getName(), form.getSurname(),
                form.getEmail(), form.getPhoneNumber(),
                form.getState(), form.getCity());

            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        } catch (DuplicateUserException e) {
            LOGGER.warn("Error in form RegisterForm, email is already in used");
            errors.rejectValue("email", "validation.user.DuplicateEmail");
            return register(form);
        }

        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false") boolean error) {
        LOGGER.info("Accessed /login GET controller with error value {}", error);

        final ModelAndView mav = new ModelAndView("views/login");
        mav.addObject("error", error);
        return mav;
    }

    //VERIFY ACCOUNT

    @RequestMapping(path = "/user/verifyAccount")
    public ModelAndView verifyAccount(HttpServletRequest request,
                                      @RequestParam(defaultValue = "") String token) {
        LOGGER.info("Accessed /user/verifyAccount GET controller");

        final Optional<User> userOptional = userService.verifyAccount(token);
        boolean success = false;
        //TODO: CAMBIAR TITULOOO
        final ModelAndView mav = new ModelAndView("views/user/account/verification/verify");
        if (userOptional.isPresent()) {
            success = true;
            User user = userOptional.get();
            LOGGER.debug("Updating user {} credentials", user.getId());
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        }
        mav.addObject("success", success);
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/resend", method = RequestMethod.POST)
    public ModelAndView resendAccountVerification(Principal principal) {
        LOGGER.info("Accessed /user/verifyAccount/resend GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        userService.resendVerificationToken(user);

        final ModelAndView mav = new ModelAndView("redirect:/user/verifyAccount/resendConfirmation");
        mav.addObject("loggedUser", user);

        return mav;
    }

    @RequestMapping("/user/verifyAccount/resendConfirmation")
    public ModelAndView verificationResendConfirmation() {
        LOGGER.info("Accessed /user/verifyAccount/resendConfirmation GET controller");
        return new ModelAndView("views/user/account/verification/resendConfirmation");
    }

    //RESET PASSWORD

    @RequestMapping(path = "/user/resetPasswordRequest")
    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form) {
        LOGGER.info("Accessed /user/resetPasswordRequest GET controller");

        return new ModelAndView("views/user/account/password/resetRequest");
    }

    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                          BindingResult errors) {
        LOGGER.info("Accessed /user/resetPasswordRequest POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form ResetPasswordForm");
            return resetPasswordRequest(form);
        }

        final Optional<User> user = userService.getUserByEmail(form.getEmail());
        //FIXME: STRING DINAMIO
        if (!user.isPresent()) {
            LOGGER.warn("Error in form ResetPasswordForm, user under email {} not found", form.getEmail());

            errors.rejectValue("email", "errors.invalidEmail");
            return resetPasswordRequest(form);
        }

        userService.generateNewPassword(user.get());

        return new ModelAndView("views/user/account/password/resetEmailConfirmation");
    }

    @RequestMapping(path = "/user/resetPassword")
    public ModelAndView resetPassword(@RequestParam(defaultValue = "") String token,
                                      @ModelAttribute("resetPasswordForm") final ResetPasswordForm form) {
        LOGGER.info("Accessed /user/resetPassword GET controller");

        if (userService.validatePasswordReset(token)) {
            final ModelAndView mav = new ModelAndView("views/user/account/password/reset");
            mav.addObject("token", token);
            return mav;
        }

        LOGGER.debug("Token reset password is invalid");
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(path = "/user/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(HttpServletRequest request,
                                      @Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
                                      BindingResult errors) {

        LOGGER.info("Accessed /user/resetPassword POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form resetPasswordForm data");
            return new ModelAndView("views/user/account/password/reset");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            LOGGER.warn("Error in form resetPasswordForm data, form passwords dont match");

            errors.rejectValue("", "validation.user.passwordsDontMatch");
            return new ModelAndView("views/user/account/password/reset");
        }

        final Optional<User> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
        boolean success = false;

        final ModelAndView mav = new ModelAndView("views/user/account/password/resetResult");
        if (userOptional.isPresent()) {

            success = true;
            User user = userOptional.get();
            LOGGER.debug("Updated user credentials");
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        }
        mav.addObject("success", success);
        return mav;
    }

    //JOIN STEPS

    @RequestMapping("/user/join")
    public ModelAndView join(@ModelAttribute("joinForm") final FirstJoinForm form, Principal principal) {
        LOGGER.info("Accessed /user/join GET controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.hasRole("PROVIDER")) {
            LOGGER.warn("User {} is already provider", user.getId());
            return new ModelAndView("redirect:/user/dashboard");
        }
        ModelAndView mav = new ModelAndView("views/user/account/roles/join");
        mav.addObject("states", locationService.getStates());
        return mav;
    }

    @RequestMapping(path = "/user/join", method = RequestMethod.POST)
    public ModelAndView joinPost(@Valid @ModelAttribute("joinForm") final FirstJoinForm form,
                                 final BindingResult errors,
                                 RedirectAttributes ra,
                                 Principal principal) {
        LOGGER.info("Accessed /user/join POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.hasRole("PROVIDER")) {
            LOGGER.warn("User {} is already provider", user.getId());
            return new ModelAndView("redirect:/user/dashboard");
        }
        if (errors.hasErrors()) {
            LOGGER.warn("Error in form FirstJoinForm data");
            return join(form, principal);
        }

        LocalTime start = LocalTime.parse(form.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime stop = LocalTime.parse(form.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));

        if (start.compareTo(stop) == 0) {
            LOGGER.warn("Error in form FirstJoinForm data, times are invalid");
            errors.rejectValue("", "validation.join.equalTime");
            return join(form, principal);
        }

        ra.addFlashAttribute("state", form.getState());
        ra.addFlashAttribute("startTime", form.getStartTime());
        ra.addFlashAttribute("endTime", form.getEndTime());
        ra.addFlashAttribute("cities", locationService.getCitiesByStateId(form.getState()));

        LOGGER.debug("Added redirect attributes to /user/join/chooseCity");

        return new ModelAndView("redirect:/user/join/chooseCity");
    }

    @RequestMapping("/user/join/chooseCity")
    public ModelAndView joinChooseCity(@ModelAttribute("chooseCityForm") final SecondJoinForm form,
                                       HttpServletRequest request, Principal principal) {
        LOGGER.info("Accessed /user/join/chooseCity GET controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.hasRole("PROVIDER")) {
            LOGGER.warn("User {} is already provider", user.getId());
            return new ModelAndView("redirect:/user/dashboard");
        }

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap == null && form.getState() == 0) {
            LOGGER.warn("Flashmap and form are null redirecting to first step");
            return new ModelAndView("redirect:/user/join");
        }

        ModelAndView mav = new ModelAndView("views/user/account/roles/chooseCity");

        if (flashMap != null)
            mav.addAllObjects(flashMap);

        //FIXME:
        if (form != null && form.getState() != 0) {
            LOGGER.debug("Adding state cities again due to error in chooseCity form");
            mav.addObject("cities", locationService.getCitiesByStateId(form.getState()));
        }



        return mav;
    }

    @RequestMapping(path = "/user/join/chooseCity", method = RequestMethod.POST)
    public ModelAndView joinChooseCityPost(@Valid @ModelAttribute("chooseCityForm") final SecondJoinForm form,
                                           final BindingResult errors,
                                           HttpServletRequest request,
                                           Principal principal) {
        LOGGER.info("Accessed /user/join/chooseCity POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.hasRole("PROVIDER")) {
            LOGGER.warn("User {} is already provider", user.getId());
            return new ModelAndView("redirect:/user/dashboard");
        }

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form SecondJoinForm data");
            return joinChooseCity(form, request, principal);
        }

        userService.makeProvider(user.getId(), form.getCity(), form.getStartTime(), form.getEndTime());

        user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        LOGGER.debug("Updating user {} credentials", user.getId());
        forceLogin(user, request);

        return new ModelAndView("redirect:/user/dashboard");
    }


    @RequestMapping(path = "/user/account")
    public ModelAndView profile(@ModelAttribute("searchForm") final SearchForm form, Principal principal) {
        LOGGER.info("Accessed /user/account GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClientId(user.getId(), 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
        mav.addObject("loggedUser", user);
        mav.addObject("results", providersContacted);
        return mav;
    }


    @RequestMapping(path = "/user/account/search")
    public ModelAndView profileSearch(@ModelAttribute("searchForm") final SearchForm form,
                                      BindingResult errors, Principal principal) {
        LOGGER.info("Accessed /user/account/search GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClientId(user.getId(), form.getPage(), 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
        mav.addObject("results", providersContacted);
        return mav;
    }

    @RequestMapping(path = "/user/account/updateInfo")
    public ModelAndView updateProfile(@ModelAttribute("userInfoForm") UserInfoForm form) {
        LOGGER.info("Accessed /user/account/updateInfo GET controller");
        return new ModelAndView("views/user/profile/editProfile");
    }

    @RequestMapping(path = "/user/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        LOGGER.info("Accessed /user/{} GET controller", userId);

        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        ModelAndView mav = new ModelAndView("views/user/profile/otherProfile");
        mav.addObject("user", user);
        return mav;
    }

    //FIXME: REVISAR EXCEPCION
    @RequestMapping(value = "/user/account/updateInfo", method = RequestMethod.POST)
    public ModelAndView updateProfileInfo(@Valid @ModelAttribute("userInfoForm") final UserInfoForm form,
                                          BindingResult errors, Principal principal) {
        LOGGER.info("Accessed /user/account/updateInfo POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form UserInfoForm data");
            return updateProfile(form);
        }

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        userService.updateUserInfo(
            new UserInfo(form.getName(), form.getSurname(),
                form.getCity(), form.getState(), form.getPhoneNumber()),
            user);

        return new ModelAndView("redirect:/user/account");
    }

    @RequestMapping(value = "/user/account/updateCoverImage", method = RequestMethod.POST)
    public ModelAndView updateCoverImage(@RequestParam("image") MultipartFile file,
                                         Principal principal) {
        LOGGER.info("Accessed /user/account/updateCoverImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypes().contains(file.getContentType())) {
            LOGGER.warn("Image content type is not valid");
            throw new IllegalContentTypeException();
        }
        try {
            userService.updateCoverImage(new ImageDto(file.getBytes(), file.getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new IllegalContentTypeException();
        }
        return new ModelAndView("redirect:/user/account");
    }


    @RequestMapping(value = "/user/account/updateProfileImage", method = RequestMethod.POST)
    public ModelAndView updateProfileImage(@RequestParam("image") MultipartFile file,
                                           Principal principal) {
        LOGGER.info("Accessed /user/account/updateProfileImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypes().contains(file.getContentType())) {
            LOGGER.warn("Image content type is not valid");
            throw new IllegalContentTypeException();
        }
        try {
            userService.updateProfileImage(new ImageDto(file.getBytes(), file.getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new IllegalContentTypeException();
        }
        return new ModelAndView("redirect:/user/account");
    }


    private void forceLogin(User user, HttpServletRequest request) {
        LOGGER.debug("forcing login of user {}", user.getId());

        //generate authentication
        final PreAuthenticatedAuthenticationToken token =
            new PreAuthenticatedAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));

        token.setDetails(new WebAuthenticationDetails(request));

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.
            stream()
            .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());
    }


}