package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.contact.AuxContactDto;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobCategory;
import ar.edu.itba.paw.models.job.Review;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import ar.edu.itba.paw.webapp.dto.request.NewContactDto;
import ar.edu.itba.paw.webapp.dto.request.NewReviewDto;
import ar.edu.itba.paw.webapp.dto.response.*;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    @Context
    private SecurityContext securityContext;

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
        LOGGER.info("Accessed /jobs/ GET controller");

        final PaginatedSearchResult<Job> results = searchService.getJobsByCategory(query, order, category, state, city, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<JobDto> jobsDto = JobDto.mapJobToDto(results.getResults(), uriInfo, securityContext);

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
        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);//FIXME agregar mensaje

        if (securityContext.getUserPrincipal() != null) {
            final Optional<User> user = userService.getUserByEmail(securityContext.getUserPrincipal().getName());
            SingleJobDto singleJobDto = new SingleJobDto(job, uriInfo, securityContext,
                user.isPresent() && userService.hasContactJobProvided(job.getProvider(), user.get(), job));
            return Response.ok(singleJobDto).build();
        }

        JobDto jobDto = new JobDto(job, uriInfo, securityContext);
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
        LOGGER.info("Accessed /jobs/{}/reviews GET controller", id);

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);//FIXME agregar mensaje

        final PaginatedSearchResult<Review> results = reviewService.getReviewsByJob(job, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<ReviewDto> reviewsDto = ReviewDto.mapReviewToDto(results.getResults(), uriInfo, securityContext);

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

    @POST
    @Path("/{id}/contact")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response contactPost(@PathParam("id") final Long id, @Valid final NewContactDto contact) {

        LOGGER.info("Accessed /jobs/{}/contact POST controller", id);

        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new);//FIXME agregar mensaje
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);//FIXME agregar mensaje

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
            userService.contact(auxContactDto, user, job.getProvider());
        } catch (IllegalContactException e) {
            throw new IllegalContactException();//FIXME agregar mensaje si se contacta a si mismo
        }

        return Response.created(JobDto.getContactUriBuilder(job, uriInfo).build()).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response newJob(
        @NotEmpty(message="{NotEmpty.newJob.jobProvided}")
        @Size(max = 50, message="Size.newJob.jobProvided")
        @Pattern(regexp = "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message="{Pattern.newJob.jobProvided}")
        @FormDataParam("jobProvided")
        final String jobProvided,
        @NotNull(message="{NotNull.newJob.jobCategory}")
        @FormDataParam("jobCategory")
        final JobCategory jobCategory,
        @NotNull(message="{NotNull.newJob.price}")
        @Range(min = 1, max = 999999, message="{Range.newJob.price}")
        @FormDataParam("price")
        final BigDecimal price,
        @NotEmpty(message = "{NotEmpty.newJob.description}")
        @Size(max = 300, message="{Size.newJob.description}")
        @FormDataParam("description")
        final String description,
        @NotNull(message = "{NotNull.newJob.paused}")
        @DefaultValue("false")
        @FormDataParam("paused")
        final Boolean paused,
        @Size(max = 6, message="{Size.newJob.images}")
        @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"}, message="ContentType.newJob.images")
        @FormDataParam("images")
            List<FormDataBodyPart> images
    ) throws IOException
    {
        LOGGER.info("Accessed /jobs/ POST controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new); //FIXME agregar mensaje

        List<NewImageDto> imagesToUpload = new LinkedList<>();

        if (images != null) {
            for (FormDataBodyPart part : images) {
                InputStream in = part.getEntityAs(InputStream.class);
                imagesToUpload.add(new NewImageDto(IOUtils.toByteArray(in), part.getMediaType().toString()));
            }
        }

        final Job job = jobService.createJob(jobProvided, jobCategory, description, price, paused, user, imagesToUpload);
        LOGGER.info("Created job with id {}", job.getId());
        return Response.created(JobDto.getJobUriBuilder(job, uriInfo).build()).build();

    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response updateJob(
        @PathParam("id") final long id,
        @NotEmpty(message="{NotEmpty.updateJob.jobProvided}")
        @Size(max = 50, message="{Size.updateJob.jobProvided}")
        @Pattern(regexp = "^[a-zA-Z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ,.'-]*$", message = "{Pattern.updateJob.jobProvided}")
        @FormDataParam("jobProvided")
        final String jobProvided,
        @NotNull(message = "{NotNull.updateJob.price}")
        @Range(min = 1, max = 999999, message = "{NotNull.updateJob.price}")
        @FormDataParam("price")
        final BigDecimal price,
        @NotEmpty(message = "{NotEmpty.updateJob.descriptiomessage}")
        @Size(max = 300, message = "{Size.updateJob.description}")
        @FormDataParam("description")
        final String description,
        @NotNull(message = "{NotNull.updateJob.paused}")
        @DefaultValue("false")
        @FormDataParam("paused")
        final Boolean paused,
        @Size(max = 6)
        @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"})
        @FormDataParam("images")
        List<FormDataBodyPart> images,
        @FormDataParam("imagesIdToDelete")
            List<Long> imagesIdToDelete
    )
    {


        LOGGER.info("Accessed /jobs/{}/ PUT controller", id);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).
            orElseThrow(UserNotFoundException::new);//FIXME agregar mensaje
        final Job job = jobService.getJobById(id).
            orElseThrow(JobNotFoundException::new);//FIXME agregar mensaje

        if (!job.getProvider().getId().equals(user.getId())) {
            LOGGER.error("Error, user with id {} is trying to update the job with id {} that belongs to user with id {}",
                user.getId(), id, job.getProvider().getId());
            throw new IllegalOperationException();//FIXME agregar mensaje
        }

        List<NewImageDto> imagesToUpload = new LinkedList<>();

        if (images != null) {
            for (FormDataBodyPart part : images) {
                InputStream in = part.getEntityAs(InputStream.class);
                try {
                    imagesToUpload.add(new NewImageDto(IOUtils.toByteArray(in), part.getMediaType().toString()));
                } catch (IOException e) {
                    LOGGER.error("Error getting bytes from images");
                    throw new ServerInternalException();//FIXME agregar mensaje
                }

            }
        }

        List<Long> imagesToDelete = imagesIdToDelete == null ? new LinkedList<>() : imagesIdToDelete;

        try {
            jobService.updateJob(jobProvided, description, price, paused, imagesToUpload, job, imagesToDelete);
        } catch (MaxImagesPerJobException e) {
            LOGGER.warn("Error max Images per job reached");
            throw new MaxImagesPerJobException();//FIXME agregar mensaje;
        }
        LOGGER.info("The job with id {} has been updated successfully", id);
        return Response.created(JobDto.getJobUriBuilder(job, uriInfo).build()).build();
    }

    @Path("/{id}/reviews")
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobReviewPost(@PathParam("id") long id, @Valid final NewReviewDto reviewDto) {
        LOGGER.info("Accessed /jobs/{}/reviews POST controller", id);
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);//FIXME: agregar mensaje
        final Job job = jobService.getJobById(id).orElseThrow(JobNotFoundException::new); //FIXME: agregar mensaje
        Review review = reviewService.createReview(reviewDto.getDescription(), job, Integer.parseInt(reviewDto.getRating()), user);
        LOGGER.info("Create review with id {} in the job with id {}", review.getId(), id);
        return Response.created(ReviewDto.getReviewUriBuilder(review, uriInfo).build()).build();
    }

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
