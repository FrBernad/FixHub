package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Schedule;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.EditJobForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class JobController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @RequestMapping("/jobs/{jobId}")
    public ModelAndView job(@ModelAttribute("reviewForm") final ReviewForm form,
                            @PathVariable("jobId") final Long jobId,
                            final Integer error,
                            @RequestParam(defaultValue = "false") final boolean paginationModal,
                            @RequestParam(defaultValue = "0") int page, Principal principal) {

        LOGGER.info("Accessed /jobs/{} GET controller", jobId);

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final Schedule schedule = userService.getScheduleByUserId(job.getProvider().getId()).orElseThrow(ScheduleNotFoundException::new);
        final ModelAndView mav = new ModelAndView("views/jobs/job");
        mav.addObject("job", job);
        mav.addObject("error", error);
        mav.addObject("location", userService.getLocationByProviderId(job.getProvider().getId()));
        PaginatedSearchResult<Review> results = reviewService.getReviewsByJob(job, page, 4);
        PaginatedSearchResult<Review> firstResults = reviewService.getReviewsByJob(job, 0, 4);
        mav.addObject("results", results);
        mav.addObject("firstResults", firstResults);
        mav.addObject("paginationModal", paginationModal);

        boolean canReview = false;
        if (principal != null) {
            final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
            canReview = userService.hasContactJobProvided(job, user);
        }

        mav.addObject("canReview", canReview);

        mav.addObject("startTime", schedule.getStartTime());
        mav.addObject("endTime", schedule.getEndTime());
        return mav;
    }

    @RequestMapping(path = "/jobs/{jobId}", method = RequestMethod.POST)
    public ModelAndView jobReviewPost(@PathVariable("jobId") final long jobId,
                                      @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                      final BindingResult errors, Principal principal) {

        LOGGER.info("Accessed /jobs/{} POST controller", jobId);

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form ReviewForm data for job {}", jobId);
            return job(form, jobId, 1, false, 0, principal);
        }

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        //Se que el job existe porque ya pedí el job en la base de datos
        final Review review = reviewService.createReview(form.getDescription(), job, Integer.parseInt(form.getRating()), user);

        final ModelAndView mav = new ModelAndView("redirect:/jobs/" + job.getId());
        return mav;
    }

    @RequestMapping("/jobs/{jobId}/edit")
    public ModelAndView updateJob(@PathVariable("jobId") final long jobId, @ModelAttribute("editJobForm") final EditJobForm form) {
        ModelAndView mav = new ModelAndView("views/jobs/editJob");

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        LOGGER.info("Accessed /jobs/{}/edit GET controller", jobId);
        mav.addObject("maxImagesPerJob", Job.MAX_IMAGES_PER_JOB);
        mav.addObject("job", job);
        return mav;
    }

    @RequestMapping(value = "/jobs/{jobId}/edit", method = RequestMethod.POST)
    public ModelAndView updateJob(@PathVariable("jobId") final long jobId, @Valid @ModelAttribute("editJobForm") final EditJobForm form, BindingResult errors, Principal principal) {

        LOGGER.info("Accessed /jobs/{}/edit POST controller", jobId);

        if (errors.hasErrors()) {
            LOGGER.warn("The form has errors");
            return updateJob(jobId, form);
        }

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        if (!job.getProvider().getId().equals(user.getId())) {
            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}", user.getId(), jobId, job.getProvider().getId());
            throw new IllegalOperationException();
        }

        List<ImageDto> imagesDto = new LinkedList<>();
        String contentType;

        if (!form.getImages().get(0).isEmpty()) {
            for (final MultipartFile image : form.getImages()) {
                try {
                    contentType = image.getContentType();
                    if (!imageService.getContentTypesNoGIF().contains(contentType)) {
                        LOGGER.error("Error updating job while creating image, content type is not valid");
                        errors.rejectValue("images", "errors.IllegalContentTypeException");
                        return updateJob(jobId, form);
                    }

                    imagesDto.add(new ImageDto(image.getBytes(), contentType));
                } catch (IOException e) {
                    LOGGER.error("Error getting bytes from images");
                    throw new ServerInternalException();
                }
            }
        }

        List<Long> imagesIdDeleted;
        if (form.getImagesIdDeleted() == null) {
            imagesIdDeleted = new LinkedList<>();
        } else {
            imagesIdDeleted = form.getImagesIdDeleted();
        }


        try {
            jobService.updateJob(form.getJobProvided(), form.getDescription(), form.getPrice(), form.isPaused(), imagesDto, job, imagesIdDeleted);
        } catch (MaxImagesPerJobException e) {
            LOGGER.warn("Error max Images per job reached");
            errors.rejectValue("images", "validation.job.ImagesMax");
            return updateJob(jobId, form);
        }

        LOGGER.info("The job with id {} has been updated successfully", jobId);

        return new ModelAndView("redirect:/jobs/{jobId}");
    }

    @RequestMapping("/jobs/{jobId}/contact")
    public ModelAndView contact(@PathVariable("jobId") final long jobId,
                                @ModelAttribute("contactForm") final ContactForm form, Principal principal) {

        LOGGER.info("Accessed /jobs/{}/contact GET controller", jobId);

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final User provider = job.getProvider();
        final ModelAndView mav;
        mav = new ModelAndView("views/contact");
        mav.addObject("job", job);
        mav.addObject("provider", provider);

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        Collection<ContactInfo> contactInfoCollection = userService.getContactInfo(user);
        LOGGER.info("Retrieved user {} contactInfo", user.getId());
        mav.addObject("contactInfoCollection", contactInfoCollection);

        return mav;
    }


    @RequestMapping(path = "/jobs/{jobId}/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@PathVariable("jobId") final long jobId,
                                    @Valid @ModelAttribute("contactForm") final ContactForm form,
                                    final BindingResult errors,
                                    @RequestParam(value = "providerEmail") final String providerEmail, Principal principal) {

        LOGGER.info("Accessed /jobs/{}/contact POST controller", jobId);

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form contactForm data for job {}", jobId);
            return contact(jobId, form, principal);
        }

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        ContactDto contactDto = new ContactDto(job, Long.valueOf(form.getContactInfoId()), user, form.getMessage(), form.getState(), form.getCity(), form.getStreet(), form.getAddressNumber(), form.getFloor(), form.getDepartmentNumber());

        userService.contact(contactDto);

        return new ModelAndView("redirect:/jobs/" + job.getId());
    }


    @RequestMapping(path = "/jobs/new")
    public ModelAndView newJob(@ModelAttribute("jobForm") final JobForm form) {

        LOGGER.info("Accessed /jobs/new GET controller");

        final ModelAndView mav;

        mav = new ModelAndView("/views/jobs/newJob");
        final Collection<JobCategory> categories = jobService.getJobsCategories();
        mav.addObject("maxImagesPerJob", Job.MAX_IMAGES_PER_JOB);
        mav.addObject("categories", categories);

        return mav;
    }

    @RequestMapping(path = "/jobs/new", method = RequestMethod.POST)
    public ModelAndView newJobPost(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors, Principal principal) {

        LOGGER.info("Accessed /jobs/new POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form JobForm data");
            return newJob(form);
        }

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        List<ImageDto> imagesDto = new LinkedList<>();
        String contentType;

        if (!form.getImages().get(0).isEmpty()) {
            for (final MultipartFile image : form.getImages()) {
                try {
                    contentType = image.getContentType();
                    if (!imageService.getContentTypesNoGIF().contains(contentType)) {
                        LOGGER.error("Error creating job while creating image, content type is not valid");
                        errors.rejectValue("images", "errors.IllegalContentTypeException");
                        return newJob(form);
                    }
                    imagesDto.add(new ImageDto(image.getBytes(), contentType));
                } catch (IOException e) {
                    LOGGER.error("Error getting bytes from images");
                    throw new ServerInternalException();
                }
            }
        }
        final Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), false, imagesDto, user);
        LOGGER.info("Created job with id {}", job.getId());
        return new ModelAndView("redirect:/jobs/" + job.getId());
    }

}
