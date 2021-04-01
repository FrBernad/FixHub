package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.UserService;
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
        if (errors.hasErrors()) {
            return job(form, jobId);
        }

        Review review = reviewService.createReview(form.getDescription(), jobId, form.getRating());
        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + jobId);
        return mav;
    }


    @RequestMapping("/jobs/{jobId}/contact")
    public ModelAndView contact() {
        return new ModelAndView("views/contact");
    }


    @RequestMapping(value = "/jobs/{jobId}/contact", method = RequestMethod.POST)
    public ModelAndView jobContactEmail(@Valid @ModelAttribute("contactForm") final ContactForm form,
                                        @RequestParam(value = "recipientEmail", defaultValue = "fran@yopmail.com") final String providerEmail,
                                        final BindingResult errors, final Locale locale) throws MessagingException {

        emailService.sendSimpleMail(form.getName(),
                form.getSurname(),
                form.getAddressNumber(),
                form.getPhoneNumber(),
                form.getMessage(),
                providerEmail, locale);

        ModelAndView mav = new ModelAndView("redirect:/discover");
        return mav;
    }

}
