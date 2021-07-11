package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.JobNotFoundException;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.webapp.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;

@Path("jobs")
@Component
public class JobController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getJobs(
        @QueryParam("query") @DefaultValue("") String query,
        @QueryParam("order") @DefaultValue("MOST_POPULAR") String order,
        @QueryParam("category") String category,
        @QueryParam("state") String state,
        @QueryParam("city") String city,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        final PaginatedSearchResult<Job> results = searchService.getJobsByCategory(query, order, category, state, city, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<JobDto> jobsDto = JobDto.mapJobToDto(results.getResults(), uriInfo);

        final PaginatedResultDto<JobDto> resultsDto =
            new PaginatedResultDto<>(
                results.getPage(),
                results.getTotalPages(),
                jobsDto);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize)
            .queryParam("order", order)
            .queryParam("query", query);

        if (category != null) {
            uriBuilder.queryParam("category", category);
        }

        if (state != null) {
            uriBuilder.queryParam("state", state);
            if (city != null) {
                uriBuilder.queryParam("city", state);
            }
        }

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<JobDto>>(resultsDto) {
        }, uriBuilder);
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response job(@PathParam("id") final Long id) {
        LOGGER.info("Accessed /jobs/{} GET controller", id);
        final Optional<Job> job = jobService.getJobById(id);
        if (!job.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        }
        JobDto jobDto = new JobDto(job.get(), uriInfo);
        return Response.ok(jobDto).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getJobReviews(
        @PathParam("id") final Long id,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);

        final PaginatedSearchResult<Review> results = reviewService.getReviewsByJob(job, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<ReviewDto> reviewsDto = ReviewDto.mapReviewToDto(results.getResults(), uriInfo);

        final PaginatedResultDto<ReviewDto> resultsDto =
            new PaginatedResultDto<>(
                results.getPage(),
                results.getTotalPages(),
                reviewsDto);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<ReviewDto>>(resultsDto) {
        }, uriBuilder);
    }


    @GET
    @Path("/categories")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategories() {
        LOGGER.info("Accessed /jobs/categories GET controller");
        final Collection<String> categories = jobService.getJobsCategories().stream().map(Enum::name).collect(Collectors.toList());

        return Response.ok(new StringCollectionResponseDto(categories)).build();
    }

/*
    public ModelAndView job(@ModelAttribute("reviewForm") final ReviewForm form,
                            @PathVariable("id") final Long id,
                            final Integer error,
                            @RequestParam(defaultValue = "false") final boolean paginationModal,
                            @RequestParam(defaultValue = "0") int page, Principal principal) {

    @GET
    @Path("/categories")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategories() {
        LOGGER.info("Accessed /jobs/categories GET controller");
        final Collection<String> categories = jobService.getJobsCategories().stream().map(Enum::name).collect(Collectors.toList());

        return Response.ok(new StringCollectionResponseDto(categories)).build();
    }


        /*LOGGER.info("Accessed /jobs/{} GET controller", id);

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
        final ModelAndView mav = new ModelAndView("views/jobs/job");
        mav.addObject("job", job);
        mav.addObject("error", error);
        PaginatedSearchResult<Review> results = reviewService.getReviewsByJob(job, page, 4);
        PaginatedSearchResult<Review> firstResults = reviewService.getReviewsByJob(job, 0, 4);
        mav.addObject("results", results);
        mav.addObject("firstResults", firstResults);
        mav.addObject("paginationModal", paginationModal);

        boolean canReview = false;
        if (principal != null) {
            final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
            canReview = userService.hasContactJobProvided(job.getProvider(), user,job);
        }

        mav.addObject("canReview", canReview);

        return mav;

 */

//    @POST
//    @Path("/new")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response newJobPost(@Valid final JobForm form) {
//
//        LOGGER.info("Accessed /jobs/new POST controller");
//
////        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        List<ImageDto> imagesDto = new LinkedList<>();
////        getImagesFromJob(imagesDto, form.getImages());
//
////        final Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), false, imagesDto, user);
////        LOGGER.info("Created job with id {}", job.getId());
//    //        return new ModelAndView("redirect:/jobs/" + job.getId());
//        return Response.ok().build();
//    }


//    @RequestMapping(path = "/jobs/new", method = RequestMethod.POST)
//    public ModelAndView newJobPost(@Valid @ModelAttribute("jobForm") final JobForm form, final BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/new POST controller");
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form JobForm data");
//            return newJob(form);
//        }
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//
//        List<ImageDto> imagesDto = new LinkedList<>();
//        getImagesFromJob(imagesDto, form.getImages());
//
//        final Job job = jobService.createJob(form.getJobProvided(), form.getJobCategory(), form.getDescription(), form.getPrice(), false, imagesDto, user);
//        LOGGER.info("Created job with id {}", job.getId());
//        return new ModelAndView("redirect:/jobs/" + job.getId());
//    }


//    @PUT
//    @Path("/{id}")
//    @Produces(value={MediaType.APPLICATION_JSON})
//    @Consumes(value={MediaType.MULTIPART_FORM_DATA})
//    public Response updateJob(@PathParam("id") final long id, @Valid final EditJobForm form, Principal principal) {
//        LOGGER.info("Accessed /jobs/{}/ POST controller", id);
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//
//        if (!job.getProvider().getId().equals(user.getId())) {
//            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}", user.getId(), id, job.getProvider().getId());
//            return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
//        }
//
//        List<ImageDto> imagesDto = new LinkedList<>();
//
//        getImagesFromJob(imagesDto, form.getImages());
//
//        List<Long> imagesIdDeleted = form.getImagesIdDeleted() == null ? new LinkedList<>(): form.getImagesIdDeleted();
//
//        try {
//            jobService.updateJob(form.getJobProvided(), form.getDescription(), form.getPrice(), form.isPaused(), imagesDto, job, imagesIdDeleted);
//        } catch (MaxImagesPerJobException e) {
//            LOGGER.warn("Error max Images per job reached");
//            errors.rejectValue("images", "validation.job.ImagesMax");
//            return updateJob(id, form);
//        }
//
//        LOGGER.info("The job with id {} has been updated successfully", id);
//
//        return new ModelAndView("redirect:/jobs/{id}");
//
//
//    }

//
//    @RequestMapping(value = "/jobs/{id}/edit", method = RequestMethod.POST)
//    public ModelAndView updateJob(@PathVariable("id") final long id, @Valid @ModelAttribute("editJobForm") final EditJobForm form, BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/{}/edit POST controller", id);
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("The form has errors");
//            return updateJob(id, form);
//        }
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//
//        if (!job.getProvider().getId().equals(user.getId())) {
//            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}", user.getId(), id, job.getProvider().getId());
//            throw new IllegalOperationException();
//        }
//
//
//        List<ImageDto> imagesDto = new LinkedList<>();
//        getImagesFromJob(imagesDto, form.getImages());
//
//        List<Long> imagesIdDeleted = form.getImagesIdDeleted() == null ? new LinkedList<>(): form.getImagesIdDeleted();
//
//        try {
//            jobService.updateJob(form.getJobProvided(), form.getDescription(), form.getPrice(), form.isPaused(), imagesDto, job, imagesIdDeleted);
//        } catch (MaxImagesPerJobException e) {
//            LOGGER.warn("Error max Images per job reached");
//            errors.rejectValue("images", "validation.job.ImagesMax");
//            return updateJob(id, form);
//        }
//
//        LOGGER.info("The job with id {} has been updated successfully", id);
//
//        return new ModelAndView("redirect:/jobs/{id}");
//    }


//
//    @RequestMapping(path = "/jobs/{id}", method = RequestMethod.POST)
//    public ModelAndView jobReviewPost(@PathVariable("id") final long id,
//                                      @Valid @ModelAttribute("reviewForm") final ReviewForm form,
//                                      final BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/{} POST controller", id);
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form ReviewForm data for job {}", id);
//            return job(form, id, 1, false, 0, principal);
//        }
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//        //Se que el job existe porque ya pedí el job en la base de datos
//        reviewService.createReview(form.getDescription(), job, Integer.parseInt(form.getRating()), user);
//
//        return new ModelAndView("redirect:/jobs/" + job.getId());
//    }


//
//    @RequestMapping("/jobs/{id}/edit")
//    public ModelAndView updateJob(@PathVariable("id") final long id, @ModelAttribute("editJobForm") final EditJobForm form) {
//        ModelAndView mav = new ModelAndView("views/jobs/editJob");
//
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//
//        LOGGER.info("Accessed /jobs/{}/edit GET controller", id);
//        mav.addObject("maxImagesPerJob", Job.MAX_IMAGES_PER_JOB);
//        mav.addObject("job", job);
//        return mav;
//    }

//
//    @RequestMapping(value = "/jobs/{id}/edit", method = RequestMethod.POST)
//    public ModelAndView updateJob(@PathVariable("id") final long id, @Valid @ModelAttribute("editJobForm") final EditJobForm form, BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/{}/edit POST controller", id);
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("The form has errors");
//            return updateJob(id, form);
//        }
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//
//        if (!job.getProvider().getId().equals(user.getId())) {
//            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}", user.getId(), id, job.getProvider().getId());
//            throw new IllegalOperationException();
//        }
//
//
//        List<ImageDto> imagesDto = new LinkedList<>();
//        getImagesFromJob(imagesDto, form.getImages());
//
//        List<Long> imagesIdDeleted = form.getImagesIdDeleted() == null ? new LinkedList<>(): form.getImagesIdDeleted();
//
//        try {
//            jobService.updateJob(form.getJobProvided(), form.getDescription(), form.getPrice(), form.isPaused(), imagesDto, job, imagesIdDeleted);
//        } catch (MaxImagesPerJobException e) {
//            LOGGER.warn("Error max Images per job reached");
//            errors.rejectValue("images", "validation.job.ImagesMax");
//            return updateJob(id, form);
//        }
//
//        LOGGER.info("The job with id {} has been updated successfully", id);
//
//        return new ModelAndView("redirect:/jobs/{id}");
//    }

//    @RequestMapping("/jobs/{id}/contact")
//    public ModelAndView contact(@PathVariable("id") final long id,
//                                @ModelAttribute("contactForm") final ContactForm form, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/{}/contact GET controller", id);
//
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//        final ModelAndView mav;
//        mav = new ModelAndView("views/contact");
//        mav.addObject("job", job);
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        Collection<ContactInfo> contactInfoCollection = user.getContactInfo();
//        LOGGER.info("Retrieved user {} contactInfo", user.getId());
//        mav.addObject("contactInfoCollection", contactInfoCollection);
//
//        return mav;
//    }

//
//    @RequestMapping(path = "/jobs/{id}/contact", method = RequestMethod.POST)
//    public ModelAndView contactPost(@PathVariable("id") final long id,
//                                    @Valid @ModelAttribute("contactForm") final ContactForm form,
//                                    final BindingResult errors,
//                                    @RequestParam(value = "providerEmail") final String providerEmail, Principal principal) {
//
//        LOGGER.info("Accessed /jobs/{}/contact POST controller", id);
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form contactForm data for job {}", id);
//            return contact(id, form, principal);
//        }
//
//        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        ContactDto contactDto = new ContactDto(job, Long.valueOf(form.getContactInfoId()), user, form.getMessage(), form.getState(), form.getCity(), form.getStreet(), form.getAddressNumber(), form.getFloor(), form.getDepartmentNumber());
//
//        userService.contact(contactDto, user, job.getProvider());
//
//        return new ModelAndView("redirect:/jobs/" + job.getId());
//    }

//
//    @RequestMapping(path = "/jobs/new")
//    public ModelAndView newJob(@ModelAttribute("jobForm") final JobForm form) {
//
//        LOGGER.info("Accessed /jobs/new GET controller");
//
//        final ModelAndView mav;
//
//        mav = new ModelAndView("/views/jobs/newJob");
//        final Collection<JobCategory> categories = jobService.getJobsCategories();
//        mav.addObject("maxImagesPerJob", Job.MAX_IMAGES_PER_JOB);
//        mav.addObject("categories", categories);
//
//        return mav;
//    }


//    private void getImagesFromJob(List<ImageDto> imagesDto,List<MultipartFile> images ){
//        if (!images.get(0).isEmpty()) {
//            for (final MultipartFile image : images) {
//                try {
//                    imagesDto.add(new ImageDto(image.getBytes(),image.getContentType()));
//                } catch (IOException e) {
//                    LOGGER.error("Error getting bytes from images");
//                    throw new ServerInternalException();
//                }
//            }
//        }
//


    private <T, K> Response createPaginationResponse(PaginatedSearchResult<T> results,
                                                     GenericEntity<PaginatedResultDto<K>> resultsDto,
                                                     UriBuilder uriBuilder) {
        if (results.getResults().isEmpty()) {
            if (results.getPage() == 0) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        final Response.ResponseBuilder response = Response.ok(resultsDto);

        addPaginationLinks(response, results, uriBuilder);

        return response.build();
    }

    private <T> void addPaginationLinks(Response.ResponseBuilder responseBuilder,
                                        PaginatedSearchResult<T> results,
                                        UriBuilder uriBuilder) {

        final int page = results.getPage();

        final int first = 0;
        final int last = results.getLastPage();
        final int prev = page - 1;
        final int next = page + 1;

        responseBuilder.link(uriBuilder.clone().queryParam("page", first).build(), "first");

        responseBuilder.link(uriBuilder.clone().queryParam("page", last).build(), "last");

        if (page != first) {
            responseBuilder.link(uriBuilder.clone().queryParam("page", prev).build(), "prev");
        }

        if (page != last) {
            responseBuilder.link(uriBuilder.clone().queryParam("page", next).build(), "next");
        }
    }

}
