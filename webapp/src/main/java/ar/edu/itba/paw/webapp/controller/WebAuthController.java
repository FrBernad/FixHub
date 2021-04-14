package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(path = "/register")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm form) {
        final ModelAndView mav = new ModelAndView("views/register");
        return mav;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, final HttpServletRequest request) {
        if (errors.hasErrors())
            return register(form);

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
    public ModelAndView login(@ModelAttribute("loginForm") final LoginForm form) {
        return new ModelAndView("views/login");
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(RedirectAttributes ra, @Valid @ModelAttribute("loginForm") final LoginForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return login(form);
        }

        //TODO: ESTA BIEN ESTO?
        final Optional<User> user = userService.getUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "validation.user.emailNotRegistered");
            return login(form);
        }

        final ModelAndView mav = new ModelAndView("redirect:/discover");
        return mav;
    }

    @RequestMapping(path = "/user/verifyAccount")
    public ModelAndView verifyAccount(HttpServletRequest request, @RequestParam String token) {

        final Optional<User> userOptional = userService.verifyAccount(token);
        boolean success = false;
        //TODO: CAMBIAR TITULOOO
        final ModelAndView mav = new ModelAndView("views/accountVerification");
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
        return new ModelAndView("views/verificationResendConfirmation");
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