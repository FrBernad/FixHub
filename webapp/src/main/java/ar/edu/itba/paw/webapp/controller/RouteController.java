package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategories;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@RequestParam("searchPhrase") final String phrase) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobsBySearchPhrase(phrase);
        mav.addObject("jobs", jobs);
        mav.addObject("searchPhrase",phrase);
        System.out.println(jobs);
        System.out.println(phrase);
        return mav;
    }

    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@PathVariable("jobId") final long jobId) {
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

    @RequestMapping("/join")
    public ModelAndView join() {
        return new ModelAndView("views/join");
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ModelAndView joinEmailPost(RedirectAttributes ra, @RequestParam("email") final String email) {
        final ModelAndView mav = new ModelAndView("redirect:/join/newService");
        ra.addFlashAttribute("email", email);
        return mav;
    }

    @RequestMapping(path = "/join/newService")
    public ModelAndView newService(@ModelAttribute("email") final String email) {

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
    public ModelAndView newServicePost(@RequestParam("jobProvided") final String jobProvided,
                                       @RequestParam("jobType") final long jobType,
                                       @RequestParam("description") final String description,
                                       @RequestParam("userId") final long userId) {

        Optional<User> user = userService.getUserById(userId);
        ModelAndView mav;
        if (user.isPresent()) {
            Job job = jobService.createJob(jobProvided, jobType, description, user.get());
            mav = new ModelAndView("redirect:/jobs/" + job.getId());
        } else {
            mav = new ModelAndView("redirect:/jobs/");
        }
        return mav;
    }

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

    @RequestMapping(path = "/join/register")
    public ModelAndView registerEmail() {

        final ModelAndView mav = new ModelAndView("views/register");
        return mav;
    }


    @RequestMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("views/contact");
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView createReview(@PathVariable("jobId") final long jobId, @RequestParam("description") final String description, @RequestParam("rating") final int rating) {
        //TODO: Service hace lo del time
        Review review = reviewService.createReview(description, jobId, rating);
        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + jobId);
        return mav;
    }
}
