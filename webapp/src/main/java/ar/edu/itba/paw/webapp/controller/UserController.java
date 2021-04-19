package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.EditProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/profile")
    public ModelAndView user(){
        final  ModelAndView mav= new ModelAndView("views/user");
        return mav;
    }

    @RequestMapping(path="/editProfile")
    public ModelAndView editProfile(@ModelAttribute("editProfileForm") EditProfileForm form){
            return new ModelAndView("views/editProfile");
    }

    @RequestMapping(path = "/editProfile", method = RequestMethod.POST)
    public ModelAndView editProfilePost(@Valid @ModelAttribute("editProfileForm") final EditProfileForm form, BindingResult errors){

        if(errors.hasErrors())
            return editProfile(form);

        final ModelAndView mav= new ModelAndView("redirect:/profile");
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        try{
            userService.updateUserInfo(user.getId(),form.getName(),form.getSurname(), form.getPhoneNumber(), form.getState(), form.getCity());

        }catch (DuplicateUserException e){
            errors.rejectValue("email","validation.user.DuplicateEmail");
            return editProfile(form);
        }

        return mav;
    }

}
