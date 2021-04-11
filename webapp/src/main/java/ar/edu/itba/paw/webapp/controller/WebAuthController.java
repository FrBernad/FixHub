package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class WebAuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register")
    public ModelAndView registerEmail(@ModelAttribute("registerForm") final RegisterForm form) {
        final ModelAndView mav = new ModelAndView("views/register");
        return mav;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerEmailPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return registerEmail(form);

        try {
            final User provider = userService.createUser(form.getPassword(), form.getName(), form.getSurname(), form.getEmail(), form.getPhoneNumber(),
                form.getState(), form.getCity());
        } catch (DuplicateUserException e) {
            errors.rejectValue("email", "validation.user.DuplicateEmail");
            return registerEmail(form);
        }

        final ModelAndView mav = new ModelAndView("redirect:/login");
        return mav;
    }


    @RequestMapping("/login")
    public ModelAndView login(@ModelAttribute("loginForm") final LoginForm form) {
        return new ModelAndView("views/login");
    }

//    @RequestMapping(path = "/login", method = RequestMethod.POST)
//    public ModelAndView loginPost(RedirectAttributes ra, @Valid @ModelAttribute("loginForm") final LoginForm form, final BindingResult errors) {
//
//        if (errors.hasErrors()) {
//            return login(form);
//        }
//
//        //TODO: ESTA BIEN ESTO?
//        final Optional<User> user = userService.getUserByEmail(form.getEmail());
//        if (!user.isPresent()) {
//            errors.rejectValue("email", "validation.user.emailNotRegistered");
//            return login(form);
//        }
//
//        final ModelAndView mav = new ModelAndView("redirect:/discover");
//        return mav;
//    }

}