package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Controller
public class JobController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmailService emailService;


    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@ModelAttribute("reviewForm") final ReviewForm form, @PathVariable("jobId") final long jobId) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (!job.isPresent()) {
            return new ModelAndView("views/pageNotFound");
        }
        final ModelAndView mav = new ModelAndView("views/job");
        mav.addObject("job", job.get());
        System.out.println(jobId);
        System.out.println(job.get());
        Collection<Review> reviews = reviewService.getReviewsByJobId(jobId);
        mav.addObject("reviews", reviews);
        return mav;
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView jobReviewPost(@PathVariable("jobId") final long jobId,
                                      @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                      final BindingResult errors) {

        if(!jobService.getJobById(jobId).isPresent()) {
            return new ModelAndView("views/pageNotFound");
        }

        if (errors.hasErrors()) {
            return job(form, jobId);
        }
        Review review = reviewService.createReview(form.getDescription(), jobId, Integer.parseInt(form.getRating()));
        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + jobId);
        return mav;
    }


    @RequestMapping("/jobs/{jobId}/contact")
    public ModelAndView contact(@PathVariable("jobId") final long jobId,
                                @ModelAttribute("contactForm") final ContactForm form) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (!job.isPresent()) {
            return new ModelAndView("views/pageNotFound");
        }
        final ModelAndView mav;
        mav = new ModelAndView("views/contact");
        mav.addObject("job", job.get());
        return mav;
    }


    @RequestMapping(value = "/jobs/{jobId}/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@PathVariable("jobId") final long jobId,
                                    @Valid @ModelAttribute("contactForm") final ContactForm form,
                                    final BindingResult errors, @RequestParam(value = "providerEmail") final String providerEmail,
                                    final Locale locale) throws MessagingException {

        if(!jobService.getJobById(jobId).isPresent()) {
            return new ModelAndView("views/pageNotFound");
        }

        if (errors.hasErrors()) {
            return contact(jobId, form);
        }

        emailService.sendSimpleMail(form.getName(),
                form.getSurname(),
                form.getAddressNumber(),
                form.getPhoneNumber(),
                form.getMessage(),
                providerEmail, locale);

        ModelAndView mav = new ModelAndView("redirect:/jobs/" + jobId);
        return mav;
    }

}