package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;
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
            User provider = userService.createUser("password", form.getName(), form.getSurname(), form.getEmail(), form.getPhoneNumber(),
                    form.getState(), form.getCity());
        } catch (org.springframework.dao.DuplicateKeyException e) {
            System.out.println("user already exists"); //habria que agregar el error de que el usuario ya existe
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

        final ModelAndView mav = new ModelAndView("redirect:/join/newService");
        ra.addFlashAttribute("email", form.getEmail());
        return mav;
    }

    @RequestMapping(path = "/join/newService")
    public ModelAndView newService(@ModelAttribute("email") final String email, @ModelAttribute("serviceForm") final ServiceForm form) {

        ModelAndView mav;

        if (email == null) {
            mav = new ModelAndView("redirect:/join");
        } else {
            Optional<User> user = userService.getUserByEmail(email);
            if (!user.isPresent()) {
                mav = new ModelAndView("redirect:/join");
            } else {
                mav = new ModelAndView("views/newService");
                mav.addObject("user", user.get());
                Collection<JobCategory> categories = jobService.getJobsCategories();
                mav.addObject("categories", categories);
            }
        }

        return mav;
    }


    @RequestMapping(path = "/join/newService", method = RequestMethod.POST)
    public ModelAndView newServicePost(@Valid @ModelAttribute("serviceForm") final ServiceForm form, final BindingResult errors,
                                       @RequestParam("userId") final long userId) {

        Optional<User> user = userService.getUserById(userId);

        if (!user.isPresent()) {
            return new ModelAndView("redirect:/jobs/");
        }

        if (errors.hasErrors()) {
            return newService(user.get().getEmail(), form);
        }

        Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), user.get());
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }

    @RequestMapping("/pageNotFound")
    public ModelAndView pageNotFound() {
        return new ModelAndView("views/pageNotFound");
    }


}
