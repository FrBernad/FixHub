package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class NewJobController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/join/register")
    public ModelAndView registerEmail(@ModelAttribute("registerForm") final RegisterForm form) {
        final ModelAndView mav = new ModelAndView("views/register");
        return mav;
    }

    @RequestMapping(path = "/join/register", method = RequestMethod.POST)
    public ModelAndView registerEmailPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return registerEmail(form);

        try {
            User provider = userService.createUser("password", form.getName(), form.getSurname(), form.getEmail(), form.getPhoneNumber(),
                    form.getState(), form.getCity());
        } catch (org.springframework.dao.DuplicateKeyException e) {
            System.out.println("user already exists"); //habria que agregar el error de que el usuario ya existe
        }

        final ModelAndView mav = new ModelAndView("redirect:/join");
        return mav;
    }

}
