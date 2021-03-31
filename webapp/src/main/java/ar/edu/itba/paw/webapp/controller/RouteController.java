package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.Review;
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

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/")
    public ModelAndView landingPage() {
        final ModelAndView mav = new ModelAndView("views/landingPage");
        Collection<Job> jobs = jobService.getJobs();
        //TODO: Ver si está bien esto
        Collection<JobCategories> categories = jobService.getJobsCategories();
        mav.addObject("jobs", jobs);
        mav.addObject("categories", categories);
        return mav;
    }

    @RequestMapping("/discover")
    public ModelAndView discover(@RequestParam(value = "filterBy",defaultValue = "0") final long filterBy, @RequestParam(value = "orderBy",defaultValue = "1") final long orderBy) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobs();
        mav.addObject("jobs", jobs);
        mav.addObject("order", orderBy);
        mav.addObject("filter", filterBy);
        return mav;
    }

    //     @RequestMapping("/discover/filter")
//    public ModelAndView discoverFilter(@RequestParam(value = "filterBy",defaultValue = "0") final long filterBy) {
//        final ModelAndView mav = new ModelAndView("views/discover");
//        Collection<Job> jobs = jobService.getJobs();
//        mav.addObject("jobs", jobs);
//        mav.addObject("filter", filterBy);
//        System.out.println(jobs);
//        return mav;
//    }

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@RequestParam("searchPhrase") final String phrase) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobsBySearchPhrase(phrase);
        mav.addObject("jobs", jobs);
        mav.addObject("searchPhrase",phrase);

        return mav;
    }

    @RequestMapping("/join")
    public ModelAndView join(@ModelAttribute("emailForm") final EmailForm form) {
        return new ModelAndView("views/join");
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ModelAndView joinEmailPost(RedirectAttributes ra, @Valid @ModelAttribute("emailForm") final EmailForm form,final BindingResult errors) {

        if(errors.hasErrors()){
            return join(form);
        }

        final ModelAndView mav = new ModelAndView("redirect:/join/newService");
        ra.addFlashAttribute("email", form.getEmail());
        return mav;
    }

    @RequestMapping(path = "/join/newService")
    public ModelAndView newService(@ModelAttribute("email") final String email,@ModelAttribute("serviceForm") final ServiceForm form) {

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

        if(errors.hasErrors()){
            return newService(user.get().getEmail(),form);
        }

        Job job = jobService.createJob(form.getJobProvided(), form.getJobCategoryId(), form.getDescription(), user.get());
        return  new ModelAndView("redirect:/jobs/" + job.getId());
    }

    @RequestMapping(path = "/join/register")
    public ModelAndView registerEmail(@ModelAttribute("registerForm") final RegisterForm form){
        final ModelAndView mav = new ModelAndView("views/register");
        return mav;
    }

    @RequestMapping(path = "/join/register", method = RequestMethod.POST)
    public ModelAndView registerEmailPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors){
        if(errors.hasErrors())
            return registerEmail(form);

        User provider;
        try {
            provider = userService.createUser("password", form.getName(), form.getSurname(), form.getEmail(), form.getPhoneNumber(),
                    form.getState(), form.getCity());
        } catch (org.springframework.dao.DuplicateKeyException e) {
            System.out.println("user already exists"); //habria que agregar el error de que el usuario ya existe
        }

        final ModelAndView mav = new ModelAndView("redirect:/join");
        return mav;
    }

    @RequestMapping("/contact")
    public ModelAndView contact(@ModelAttribute("contactForm") final ContactForm form) {
        return new ModelAndView("views/contact");
    }

    @RequestMapping(value = "/contact",method = RequestMethod.POST)
    public ModelAndView contact(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return contact(form);
        }
        final ModelAndView mav = new ModelAndView("redirect:/");
        return mav;

    }

    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@ModelAttribute("reviewForm") final ReviewForm form, @PathVariable("jobId") final long jobId) {
        final ModelAndView mav;
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            mav = new ModelAndView("views/job");
            mav.addObject("job", job.get());
            Collection<Review> reviews = reviewService.getReviewsByJobId(jobId);
            mav.addObject("reviews", reviews);
        } else {
            mav = new ModelAndView("views/pageNotFound");
        }
        return mav;
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView jobReviewPost(@PathVariable("jobId") final long jobId, @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                     final BindingResult errors) {
        //TODO: Service hace lo del time
        if(errors.hasErrors()){
            return job(form,jobId);
        }

        Review review = reviewService.createReview(form.getDescription(), jobId, form.getRating());
        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + jobId);
        return mav;
    }
}
