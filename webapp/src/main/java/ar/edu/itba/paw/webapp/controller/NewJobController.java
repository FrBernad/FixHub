package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.JobForm;
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

    /*FIXME: sacar el constructor  de user default cuando haya autenticación*/
    @RequestMapping(path = "/join/newJob")
    public ModelAndView newJob(@ModelAttribute("user") final User user, @ModelAttribute("jobForm") final JobForm form) {

        final ModelAndView mav;

        //TODO: CAMBIAR EL FLUJO CUANDO TENGAMOS AUTENTICACION

        if (user.getEmail() == null) {
            mav = new ModelAndView("redirect:/login");
        } else {
            mav = new ModelAndView("/views/newJob");
            mav.addObject("user", user);
            Collection<JobCategory> categories = jobService.getJobsCategories();
            mav.addObject("categories", categories);
        }


        return mav;
    }


    @RequestMapping(path = "/join/newJob", method = RequestMethod.POST)
    public ModelAndView newJobPost(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors,
                                   @RequestParam("userId") final long userId) {

        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (errors.hasErrors()) {
            return newJob(user, form);
        }

        Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), user);
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }


}
