package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.EmailForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class NewJobController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

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
            final User provider = userService.createUser("password", form.getName(), form.getSurname(), form.getEmail(), form.getPhoneNumber(),
                    form.getState(), form.getCity());
        } catch (DuplicateUserException e) {
            errors.rejectValue("email", "validation.user.DuplicateEmail");
            return registerEmail(form);
        }

        final ModelAndView mav = new ModelAndView("redirect:/join");
        return mav;
    }


    @RequestMapping("/join")
    public ModelAndView join(@ModelAttribute("emailForm") final EmailForm form) {
        return new ModelAndView("views/join");
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ModelAndView joinEmailPost(RedirectAttributes ra, @Valid @ModelAttribute("emailForm") final EmailForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return join(form);
        }

        //TODO: ESTA BIEN ESTO?
        final Optional<User> user = userService.getUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            errors.rejectValue("email", "validation.user.emailNotRegistered");
            return join(form);
        }

        final ModelAndView mav = new ModelAndView("redirect:/join/newService");
        ra.addFlashAttribute("user", user);
        return mav;
    }

    @RequestMapping(path = "/join/newService")
    public ModelAndView newService(@ModelAttribute("user") final User user, @ModelAttribute("serviceForm") final ServiceForm form) {

        final ModelAndView mav;

        //TODO: CAMBIAR EL FLUJO CUANDO TENGAMOS AUTENTICACION

        if (user.getEmail() == null) {
            mav = new ModelAndView("redirect:/join");
        } else {
            mav = new ModelAndView("views/newService");
            mav.addObject("user", user);
            Collection<JobCategory> categories = jobService.getJobsCategories();
            mav.addObject("categories", categories);
        }


        return mav;
    }


    @RequestMapping(path = "/join/newService", method = RequestMethod.POST)
    public ModelAndView newServicePost(@Valid @ModelAttribute("serviceForm") final ServiceForm form, final BindingResult errors,
                                       @RequestParam("userId") final long userId) {

        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (errors.hasErrors()) {
            return newService(user, form);
        }

        Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), user);
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }


}
