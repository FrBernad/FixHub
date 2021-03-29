package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @RequestMapping("/")
    public ModelAndView landingPage() {
        final ModelAndView mav = new ModelAndView("views/landingPage");
        Collection<Job> jobs = jobService.getJobs();
        mav.addObject("jobs", jobs);
        return mav;
    }

    @RequestMapping("/discover")
    public ModelAndView discover() {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobs();
        mav.addObject("jobs", jobs);
        System.out.println(jobs);
        return mav;

    }

    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@PathVariable("jobId") final long jobId) {
        final ModelAndView mav;
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            mav = new ModelAndView("views/job");
            System.out.println(job);
            mav.addObject("job", job.get());
        } else {
            mav = new ModelAndView("views/pageNotFound");
        }
        return mav;
    }

    @RequestMapping("/join")
    public ModelAndView join() {
        return new ModelAndView("views/join");
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ModelAndView joinEmailPost(@RequestParam("jobProvided") final String email) {
        final ModelAndView mav = new ModelAndView("/join/newService/");
        mav.addObject("email", email);
        return mav;
    }

    @RequestMapping(path = "/join/newService")
    public ModelAndView createServicePost() {
        ModelAndView mav = new ModelAndView("views/newService");
        Collection<JobCategories> categories = jobService.getJobsCategories();
        mav.addObject("categories",categories);
        return mav;
    }

    @RequestMapping(path = "/join/newService", method = RequestMethod.POST)
    public ModelAndView createServicePost(@RequestParam("jobProvided") final String jobProvided, @RequestParam("jobType") final String jobType, @RequestParam("description") final String description,
                                          @RequestParam("name") final String name, @RequestParam("surname") final String surname,
                                          @RequestParam("city") final String city, @RequestParam("state") final String state,
                                          @RequestParam("phoneNumber") final String phoneNumber, @RequestParam("email") final String email) {


    @RequestMapping(path = "/join/register", method = RequestMethod.POST)
    public ModelAndView registerEmailPost(@RequestParam("name") final String name,
                                          @RequestParam("surname") final String surname,
                                          @RequestParam("email") final String email,
                                          @RequestParam("phoneNumber") final String phoneNumber,
                                          @RequestParam("state") final String state,
                                          @RequestParam("city") final String city) {
        User provider;
        try {
            provider = userService.createUser("password", name, surname, email, phoneNumber, state, city);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            System.out.println("user already exists");
        }

        final ModelAndView mav = new ModelAndView("redirect:/join");
        return mav;
    }

//
//    @RequestMapping(path = "/join/register", method = RequestMethod.POST)
//    public ModelAndView registerEmailPost() {
//
//        final ModelAndView mav = new ModelAndView("redirect:/join");
//        return mav;
//    }

    @RequestMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("views/contact");
    }

}
