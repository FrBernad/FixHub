package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.contact.AuxContactDto;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import ar.edu.itba.paw.webapp.dto.request.NewContactDto;
import ar.edu.itba.paw.webapp.dto.request.NewReviewDto;
import ar.edu.itba.paw.webapp.dto.response.JobContactDto;
import ar.edu.itba.paw.webapp.dto.response.JobDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.dto.response.SearchOptionDto;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("jobs")
@Component
public class JobController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobService jobService;

    @Autowired
    private int maxRequestSize;

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Autowired
    private ImageService imageService;

    private static final int MAX_TIME = 31536000;

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
        LOGGER.info("Accessed /jobs/ GET controller");

        final PaginatedSearchResult<Job> results = searchService.getJobsByCategory(query, order, category, state, city, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<JobDto> jobsDto = JobDto.mapJobToDto(results.getResults(), uriInfo);

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

        return createPaginationResponse(results, new GenericEntity<Collection<JobDto>>(jobsDto) {
        }, uriBuilder);
    }


    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response newJob(
        @Context final HttpServletRequest request,
        @NotEmpty(message = "{NotEmpty.newJob.jobProvided}")
        @Size(max = 50, message = "{Size.newJob.jobProvided}")
        @Pattern(regexp = "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message = "{Pattern.newJob.jobProvided}")
        @FormDataParam("jobProvided") final String jobProvided,
        @NotNull(message = "{NotNull.newJob.jobCategory}")
        @FormDataParam("jobCategory") final JobCategory jobCategory,
        @NotNull(message = "{NotNull.newJob.price}")
        @Range(min = 1, max = 999999, message = "{Range.newJob.price}")
        @FormDataParam("price") final BigDecimal price,
        @NotEmpty(message = "{NotEmpty.newJob.description}")
        @Size(max = 300, message = "{Size.newJob.description}")
        @FormDataParam("description") final String description,
        @NotNull(message = "{NotNull.newJob.paused}")
        @DefaultValue("false")
        @FormDataParam("paused") final Boolean paused,
        @Size(max = 6, message = "{Size.newJob.images}")
        @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"}, message = "{ContentType.newJob.images}")
        @FormDataParam("images")
            List<FormDataBodyPart> images
    ) throws IOException {
        LOGGER.info("Accessed /jobs/ POST controller");

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        List<NewImageDto> imagesToUpload = new LinkedList<>();

        if (images != null) {
            for (FormDataBodyPart part : images) {
                InputStream in = part.getEntityAs(InputStream.class);
                imagesToUpload.add(new NewImageDto(StreamUtils.copyToByteArray(in), part.getMediaType().toString()));
            }
        }

        final Job job = jobService.createJob(jobProvided, jobCategory, description, price, paused, user, imagesToUpload);
        LOGGER.info("Created job with id {}", job.getId());
        return Response.created(JobDto.getJobUriBuilder(job, uriInfo).build()).build();

    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response job(@PathParam("id") final Long id) {
        LOGGER.info("Accessed /jobs/{} GET controller", id);
        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);

        JobDto jobDto = new JobDto(job, uriInfo);
        return Response.ok(jobDto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response updateJob(
        @Context final HttpServletRequest request,
        @PathParam("id") final long id,
        @NotEmpty(message = "{NotEmpty.updateJob.jobProvided}")
        @Size(max = 50, message = "{Size.updateJob.jobProvided}")
        @Pattern(regexp = "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message = "{Pattern.updateJob.jobProvided}")
        @FormDataParam("jobProvided") final String jobProvided,
        @NotNull(message = "{NotNull.updateJob.price}")
        @Range(min = 1, max = 999999, message = "{Size.updateJob.price}")
        @FormDataParam("price") final BigDecimal price,
        @NotEmpty(message = "{NotEmpty.updateJob.description}")
        @Size(max = 300, message = "{Size.updateJob.description}")
        @FormDataParam("description") final String description,
        @NotNull(message = "{NotNull.updateJob.paused}")
        @DefaultValue("false")
        @FormDataParam("paused") final Boolean paused,
        @Size(max = 6, message = "{Size.updateJob.images}")
        @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"}, message = "{Size.updateJob.images}")
        @FormDataParam("images") List<FormDataBodyPart> images,
        @FormDataParam("imagesIdToDelete") List<Long> imagesIdToDelete
    ) {
        LOGGER.info("Accessed /jobs/{}/ PUT controller", id);

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).
            orElseThrow(UserNotFoundException::new);
        final Job job = jobService.getJobById(id).
            orElseThrow(JobNotFoundException::new);

        if (!job.getProvider().getId().equals(user.getId())) {
            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}",
                user.getId(), id, job.getProvider().getId());
            throw new IllegalOperationException();
        }

        List<NewImageDto> imagesToUpload = new LinkedList<>();

        if (images != null) {
            for (FormDataBodyPart part : images) {
                InputStream in = part.getEntityAs(InputStream.class);
                try {
                    imagesToUpload.add(new NewImageDto(StreamUtils.copyToByteArray(in), part.getMediaType().toString()));
                } catch (IOException e) {
                    LOGGER.error("Error getting bytes from images");
                    throw new ServerInternalException();
                }
            }
        }

        List<Long> imagesToDelete = imagesIdToDelete == null ? new LinkedList<>() : imagesIdToDelete;

        try {
            jobService.updateJob(jobProvided, description, price, paused, imagesToUpload, job, imagesToDelete);
        } catch (MaxImagesPerJobException e) {
            LOGGER.warn("Error max Images per job reached");
            throw new MaxImagesPerJobException();
        }
        LOGGER.info("The job with id {} has been updated successfully", id);
        return Response.created(JobDto.getJobUriBuilder(job, uriInfo).build()).build();
    }

    @GET
    @Path("/{jobId}/images/{id}")
    @Produces({"image/*", javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getJobImage(@PathParam("jobId") long jobId, @PathParam("id") long id, @Context Request request) {
        LOGGER.info("Accessed /jobs/images/{} GET controller", id);

        final Job job = jobService.getJobById(jobId).orElseThrow(JobNotFoundException::new);

        final Image img = imageService.getImageByJob(job, id).orElseThrow(ImageNotFoundException::new);
        LOGGER.info("Response image with id {}", id);

        final byte[] jobImage = img.getData();

        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoTransform(false);
        cacheControl.getCacheExtension().put("public", null);
        cacheControl.setMaxAge(MAX_TIME);
        cacheControl.getCacheExtension().put("immutable", null);

        return Response.ok(jobImage)
            .type(img.getMimeType())
            .cacheControl(cacheControl)
            .build();
    }

    @GET
    @Path("/searchOptions")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSearchOptions() {
        LOGGER.info("Accessed /jobs/searchOptions GET searchOptions");
        final Collection<SearchOptionDto> searchOptions = new ArrayList<>();
        searchOptions.add(new SearchOptionDto("categories", jobService.getJobsCategories()));
        searchOptions.add(new SearchOptionDto("order", jobService.getJobsOrder()));

        return Response.ok(new GenericEntity<Collection<SearchOptionDto>>(searchOptions) {
        }).build();
    }

    @POST
    @Path("/{id}/contact")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response contactPost(@PathParam("id") final Long id, @Valid final NewContactDto contact) {

        LOGGER.info("Accessed /jobs/{}/contact POST controller", id);

        if (contact == null) {
            throw new ContentExpectedException();
        }

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        if (job.isPaused()) {
            throw new IllegalOperationException();
        }

        AuxContactDto auxContactDto = new AuxContactDto(
            job,
            Long.valueOf(contact.getContactInfoId()),
            user, contact.getMessage(),
            contact.getState(),
            contact.getCity(),
            contact.getStreet(),
            contact.getAddressNumber(),
            contact.getFloor(),
            contact.getDepartmentNumber()
        );
        try {
            final JobContact jobContact = userService.contact(auxContactDto, user, job.getProvider());
            return Response.created(JobContactDto.getContactUriBuilder(jobContact, uriInfo).build()).build();
        } catch (IllegalContactException e) {
            throw new IllegalContactException();
        }
    }

    @GET
    @Path("/{id}/contact")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response hasContactPost(@PathParam("id") final Long id) {

        LOGGER.info("Accessed /jobs/{}/contact GET controller", id);

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final boolean hasContacted = userService.hasContactJobProvided(job.getProvider(), user, job);

        if (!hasContacted) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.noContent().build();
    }


    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getJobReviews(
        @PathParam("id") final Long id,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /jobs/{}/reviews GET controller", id);

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);

        final PaginatedSearchResult<Review> results = reviewService.getReviewsByJob(job, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<ReviewDto> reviewsDto = ReviewDto.mapReviewToDto(results.getResults(), uriInfo);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<Collection<ReviewDto>>(reviewsDto) {
        }, uriBuilder);
    }

    @Path("/{id}/reviews")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobReviewPost(@PathParam("id") long id, @Valid final NewReviewDto reviewDto) {
        LOGGER.info("Accessed /jobs/{}/reviews POST controller", id);
        if (reviewDto == null) {
            throw new ContentExpectedException();
        }
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);
        Review review = reviewService.createReview(reviewDto.getDescription(), job, Integer.parseInt(reviewDto.getRating()), user);
        LOGGER.info("Create review with id {} in the job with id {}", review.getId(), id);
        return Response.created(ReviewDto.getReviewUriBuilder(review, uriInfo).build()).build();
    }

    private <T, K> Response createPaginationResponse(PaginatedSearchResult<T> results,
                                                     GenericEntity<K> resultsDto,
                                                     UriBuilder uriBuilder) {
        if (results.getResults().isEmpty()) {
            if (results.getPage() == 0) {
                return Response.noContent().build();
            } else {
                return Response.status(NOT_FOUND).build();
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
