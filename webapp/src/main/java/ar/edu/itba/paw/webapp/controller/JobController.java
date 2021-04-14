package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.JobNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class JobController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;


    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@ModelAttribute("reviewForm") final ReviewForm form, @PathVariable("jobId") final Long jobId, final Integer error) {
        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final ModelAndView mav = new ModelAndView("views/job");
        mav.addObject("job", job);
        mav.addObject("error", error);
        Collection<Review> reviews = reviewService.getReviewsByJobId(job);
        mav.addObject("reviews", reviews);
        return mav;
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView jobReviewPost(@PathVariable("jobId") final long jobId,
                                      @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                      final BindingResult errors) {

        if (errors.hasErrors())
            return job(form, jobId, 1);

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        //Se que el job existe porque ya pedí el job en la base de datos
        final Review review = reviewService.createReview(form.getDescription(), job, Integer.parseInt(form.getRating()));

        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + job.getId());
        return mav;
    }


    @RequestMapping("/jobs/{jobId}/contact")
    public ModelAndView contact(@PathVariable("jobId") final long jobId,
                                @ModelAttribute("contactForm") final ContactForm form) {

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final User provider = job.getProvider();

        final ModelAndView mav;
        mav = new ModelAndView("views/contact");
        mav.addObject("job", job);
        mav.addObject("provider", provider);
        return mav;
    }


    @RequestMapping(value = "/jobs/{jobId}/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@PathVariable("jobId") final long jobId,
                                    @Valid @ModelAttribute("contactForm") final ContactForm form,
                                    final BindingResult errors,
                                    @RequestParam(value = "providerEmail") final String providerEmail) {


        if (errors.hasErrors()) {
            return contact(jobId, form);
        }

        final String address = String.format("%s, %s, %s %s, %s %s", form.getState(), form.getCity(),
            form.getStreet(), form.getAddressNumber(), form.getFloor(), form.getDepartmentNumber());

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        final Map<String, Object> mailAttrs = new HashMap<>();

        mailAttrs.put("name", form.getName());
        mailAttrs.put("susrname", form.getSurname());
        mailAttrs.put("addres", address);
        mailAttrs.put("phoneNumber", form.getName());
        mailAttrs.put("message", form.getName());

        //FIXME: VER Q ONDA ESTO
        try {
            emailService.sendMail("jobRequest", "New job request", mailAttrs);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView("redirect:/jobs/" + job.getId());
        return mav;
    }


    @RequestMapping(path = "/jobs/new")
    public ModelAndView newJob(@ModelAttribute("jobForm") final JobForm form) {

        final ModelAndView mav;

        mav = new ModelAndView("/views/newJob");
        final Collection<JobCategory> categories = jobService.getJobsCategories();
        mav.addObject("categories", categories);

        return mav;
    }


    @RequestMapping(path = "/jobs/new", method = RequestMethod.POST)
    public ModelAndView newJobPost(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return newJob(form);
        }

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), user);
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }


}
