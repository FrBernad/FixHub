package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.JobForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.Collection;

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
        final UserSchedule userSchedule = userService.getScheduleByUserId(job.getProvider().getId()).orElseThrow(ScheduleNotFoundException::new);
        final ModelAndView mav = new ModelAndView("views/jobs/job");
        mav.addObject("job", job);
        mav.addObject("error", error);
        mav.addObject("location", userService.getLocationByProviderId(job.getProvider().getId()));
        PaginatedSearchResult<Review> results = reviewService.getReviewsByJobId(job.getId(), page, 5);
        mav.addObject("results", results);
        mav.addObject("paginationModal", paginationModal);

        boolean canReview = false;
        if (principal != null) {
            final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
            canReview = userService.hasContactJobProvided(job, user);
        }

        mav.addObject("canReview", canReview);

        mav.addObject("startTime", userSchedule.getStartTime());
        mav.addObject("endTime", userSchedule.getEndTime());
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
    public ModelAndView updateJob(@PathVariable("jobId") final long jobId, @ModelAttribute("jobForm") final JobForm form){
        ModelAndView mav = new ModelAndView("views/jobs/editJob");

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);
        final Collection<JobCategory> categories = jobService.getJobsCategories();

        LOGGER.info("Accessed /jobs/{}/edit GET controller", jobId);

        mav.addObject("categories", categories);
        mav.addObject("job",job);
        return mav;
    }
    @RequestMapping(value = "/jobs/{jobId}/edit", method = RequestMethod.POST)
    public ModelAndView updateJob(@PathVariable("jobId") final long jobId, @Valid @ModelAttribute("jobForm") final JobForm form,BindingResult errors){

        if (errors.hasErrors()){
            return updateJob(jobId,form);
        }

        List<ImageDto> imagesDto = new LinkedList<>();
        String contentType;

        LOGGER.info("Accessed /jobs/{}/edit POST controller", jobId);

        //FIXME: SOLUCIONAR ESTO
        if (form.getImages().get(0).getSize() != 0) {
            for (final MultipartFile image : form.getImages()) {
                try {
                    contentType = image.getContentType();
                    if(!imageService.getContentTypes().contains(contentType))
                        throw new IllegalContentTypeException();

                    imagesDto.add(new ImageDto(image.getBytes(), contentType));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        jobService.updateJob(form.getJobProvided(),form.getJobCategory(),form.getDescription(),form.getPrice(),imagesDto,jobId);

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

        //FIXME: SOLUCIONAR ESTO
        if (form.getImages().get(0).getSize() != 0) {
            for (final MultipartFile image : form.getImages()) {
                try {
                    contentType = image.getContentType();
                    if (!imageService.getContentTypes().contains(contentType)) {
                        LOGGER.error("Error creating image, content type is not valid");
                        throw new IllegalContentTypeException();
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

    //FIXME: revisar si esta bien el principal aca y que sea el que tiene que ser. Revisar si no hay que agregarlo al webAuthConfig
    @RequestMapping(path = "jobs/images/delete/{imageId}")
    public ModelAndView deleteJobImage(@PathVariable("imageId") long imageId, @RequestParam("jobId") long jobId,Principal principal){

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        LOGGER.info("Trying to delete image with id {} from job with id {} by user with id {}",imageId,jobId,user.getId());

        Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        if(!job.getProvider().getId().equals(user.getId())){
            LOGGER.error("Error, user with id {} is trying to delete an image with id {} that belongs to user with id {}",user.getId(),imageId,job.getProvider().getId());
            throw new IllegalOperationException();
        }
        jobService.deleteImageFromJob(jobId,imageId,user);

        LOGGER.info("Image with id {} from job with id{} deleted successfully",imageId,jobId);

        return new ModelAndView("redirect:/jobs/"+jobId+"/edit");

    }

    //FIXME: SOLUCIONAR
    @RequestMapping(path = "jobs/images/{imageId}",
        produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
        method = RequestMethod.GET)
    @ResponseBody
    public byte[] getJobImage(@PathVariable("imageId") long imageId) {
        LOGGER.info("Accessed jobs/images/{} GET controller", imageId);

        Image image = imageService.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }

    //FIXME: SOLUCIONAR
    @RequestMapping(path = "/user/images/profile/{imageId}",
        produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
        method = RequestMethod.GET)
    @ResponseBody
    public byte[] getProfileImage(@PathVariable("imageId") long imageId) {
        LOGGER.info("Accessed /user/images/profile/{} GET controller", imageId);

        Image image = imageService.getImageById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }


}
