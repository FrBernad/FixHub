package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;


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
                Collection<JobCategories> categories = jobService.getJobsCategories();
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

        Job job = jobService.createJob(form.getJobProvided(), form.getJobCategoryId(), form.getDescription(), user.get());
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }

}
