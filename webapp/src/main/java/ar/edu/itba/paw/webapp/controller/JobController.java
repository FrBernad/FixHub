package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ReviewException;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.JobNotFoundException;
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
        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        final ModelAndView mav = new ModelAndView("views/job");
        mav.addObject("job", job);
        Collection<Review> reviews = reviewService.getReviewsByJobId(job);
        mav.addObject("reviews", reviews);
        return mav;
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView jobReviewPost(@PathVariable("jobId") final long jobId,
                                      @Valid @ModelAttribute("reviewfForm") final ReviewForm form,
                                      final BindingResult errors) {

        if (errors.hasErrors())
            return job(form, jobId);

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
        mav.addObject("provider",provider);
        return mav;
    }


    @RequestMapping(value = "/jobs/{jobId}/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@PathVariable("jobId") final long jobId,
                                    @Valid @ModelAttribute("contactForm") final ContactForm form,
                                    final BindingResult errors,
                                    @RequestParam(value = "providerEmail") final String providerEmail,
                                    final Locale locale) throws MessagingException {


        if (errors.hasErrors()) {
            return contact(jobId, form);
        }

        String address = String.format("%s, %s, %s %s, %s",form.getState(),form.getCity(),
                form.getStreet(),form.getAddressNumber(),form.getFloor());

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        emailService.sendSimpleMail(form.getName(),
                form.getSurname(),
                address,
                form.getPhoneNumber(),
                form.getMessage(),
                providerEmail, locale);

        ModelAndView mav = new ModelAndView("redirect:/jobs/" + job.getId());
        return mav;
    }

}
