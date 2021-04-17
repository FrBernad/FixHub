package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.ResetPasswordEmailForm;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form) {
        return new ModelAndView("views/register");
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, final HttpServletRequest request) {
        if (errors.hasErrors()) {
            if (!form.getPassword().equals(form.getConfirmPassword())) {
                //Global error, that's why it has "".
                errors.rejectValue("", "validation.user.passwordsDontMatch");
            }
            return register(form);
        }


        User user;
        final ModelAndView mav = new ModelAndView("redirect:/discover");
        try {
            user = userService.createUser(form.getPassword(),
                form.getName(), form.getSurname(),
                form.getEmail(), form.getPhoneNumber(),
                form.getState(), form.getCity());
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        } catch (DuplicateUserException e) {
            errors.rejectValue("email", "validation.user.DuplicateEmail");
            return register(form);
        }

        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error",defaultValue = "false") boolean error) {
        final ModelAndView mav = new ModelAndView("views/login");
        mav.addObject("error",error);
        return mav;
    }

    //VERIFY ACCOUNT

    @RequestMapping(path = "/user/verifyAccount")
    public ModelAndView verifyAccount(HttpServletRequest request, @RequestParam(defaultValue = "") String token) {

        final Optional<User> userOptional = userService.verifyAccount(token);
        boolean success = false;
        //TODO: CAMBIAR TITULOOO
        final ModelAndView mav = new ModelAndView("views/user/account/verify");
        if (userOptional.isPresent()) {
            success = true;
            User user = userOptional.get();
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        }
        mav.addObject("success", success);
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount/resend", method = RequestMethod.POST)
    public ModelAndView resendAccountVerification() {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        userService.resendVerificationToken(user);

        final ModelAndView mav = new ModelAndView("redirect:/user/verifyAccount/resendConfirmation");
        mav.addObject("loggedUser", user);

        return mav;
    }

    @RequestMapping("/user/verifyAccount/resendConfirmation")
    public ModelAndView verificationResendConfirmation() {
        return new ModelAndView("views/user/account/resendVerification");
    }

    //RESET PASSWORD

    @RequestMapping(path = "/user/resetPasswordRequest")
    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form) {
        return new ModelAndView("views/user/account/password/resetRequest");
    }

    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
                                          BindingResult errors) {

        if (errors.hasErrors())
            return resetPasswordRequest(form);

        final Optional<User> user = userService.getUserByEmail(form.getEmail());
        //FIXME: STRING DINAMIO
        if (!user.isPresent()) {
            errors.rejectValue("email", "errors.invalidEmail");
            return resetPasswordRequest(form);
        }

        userService.generateNewPassword(user.get());

        return new ModelAndView("views/user/account/password/resetEmailConfirmation");
    }

    @RequestMapping(path = "/user/resetPassword")
    public ModelAndView resetPassword(@RequestParam(defaultValue = "") String token,@ModelAttribute("resetPasswordForm") final ResetPasswordForm form) {
        if (userService.validatePasswordReset(token)) {
            final ModelAndView mav = new ModelAndView("views/user/account/password/reset");
            mav.addObject("token", token);
            return mav;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(path = "/user/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(HttpServletRequest request,
                                      @Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
                                      BindingResult errors) {

        if (errors.hasErrors())
            return new ModelAndView("views/user/account/password/reset");

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("", "validation.user.passwordsDontMatch");
            return new ModelAndView("views/user/account/password/reset");
        }

        final Optional<User> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
        boolean success = false;

        final ModelAndView mav = new ModelAndView("views/user/account/password/resetResult");
        if (userOptional.isPresent()) {
            success = true;
            User user = userOptional.get();
            forceLogin(user, request);
            mav.addObject("loggedUser", user);
        }
        mav.addObject("success", success);
        return mav;
    }

    @RequestMapping("/user/join")
    public ModelAndView join() {
        return new ModelAndView("views/join");
    }

    private void forceLogin(User user, HttpServletRequest request) {
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
            .map((role) -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
    }


}