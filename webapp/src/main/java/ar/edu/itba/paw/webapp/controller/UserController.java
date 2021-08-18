package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.image.NewImageDto;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import ar.edu.itba.paw.webapp.dto.request.NewStatusDto;
import ar.edu.itba.paw.webapp.dto.response.*;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static ar.edu.itba.paw.models.job.JobStatus.*;
import static javax.ws.rs.core.Response.Status.*;


@Path("/users")
@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private NotificationService notificationService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private int maxRequestSize;

    @Autowired
    private JobService jobService;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerUser(@Valid final RegisterDto registerDto) throws DuplicateUserException {
        LOGGER.info("Accessed /users/ POST controller");
        if (registerDto == null) {
            throw new ContentExpectedException();
        }
        User user;
        try {
            user = userService.createUser(registerDto.getPassword(),
                registerDto.getName(), registerDto.getSurname(),
                registerDto.getEmail(), registerDto.getPhoneNumber(),
                registerDto.getState(), registerDto.getCity());
        } catch (DuplicateUserException e) {
            LOGGER.warn("Error in registerDto RegisterForm, email is already in used");
            throw new DuplicateUserException();
        }

        return Response.created(UserDto.getUserUriBuilder(user, uriInfo).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser(@PathParam("id") final long id) {
        LOGGER.info("Accessed /users/{} GET controller", id);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        LOGGER.info("Return user with id {}", id);
        return Response.ok(new UserDto(user, uriInfo, securityContext)).build();
    }

    @GET
    @Path("/{id}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage(@PathParam("id") final long id, @Context Request request) {
        LOGGER.info("Accessed /users/{}/profileImage GET controller", id);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final Image img = user.getProfileImage();

        if (img == null) {
            return Response.status(NOT_FOUND).build();
        }

        final EntityTag eTag = new EntityTag(String.valueOf(img.getId()));

        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);

        if (responseBuilder == null) {
            final byte[] profileImage = img.getData();
            responseBuilder = Response.ok(profileImage).type(img.getMimeType()).tag(eTag);
        }

        return responseBuilder.cacheControl(cacheControl).build();
    }


    @PUT
    @Path("/{id}/profileImage")
    public Response updateUserProfileImage(@Context final HttpServletRequest request,
                                           @NotNull(message = "{NotEmpty.profileImage.image}")
                                           @ImageTypeConstraint(contentType = {"image/png", "image/jpeg", "image/gif"}, message = "{ContentType.newJob.images}")
                                           @FormDataParam("profileImage") FormDataBodyPart profileImage,
                                           @PathParam("id") final long id) throws IOException {

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }


        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        LOGGER.info("Accessed /user/{}/profileImage PUT controller", id);

        InputStream in = profileImage.getEntityAs(InputStream.class);
        userService.updateProfileImage(new NewImageDto(StreamUtils.copyToByteArray(in), profileImage.getMediaType().toString()), user);
//       FIXME: DEVOLVER LOCATION DEL RESOURCE VAMBIADO ACA Y DONDE HAGA FALTA
        return Response.ok().build();
    }


    @GET
    @Path("/{id}/coverImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserCoverImage(@PathParam("id") final long id, @Context Request request) {
        LOGGER.info("Accessed /users/{}/coverImage GET controller", id);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final Image img = user.getCoverImage();

        if (img == null) {
            return Response.status(NOT_FOUND).build();
        }

        final EntityTag eTag = new EntityTag(String.valueOf(img.getId()));

        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);

        if (responseBuilder == null) {
            final byte[] coverImage = img.getData();
            responseBuilder = Response.ok(coverImage).type(img.getMimeType()).tag(eTag);
        }

        return responseBuilder.cacheControl(cacheControl).build();
    }


    @PUT
    @Path("/{id}/coverImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserCoverImage(@Context final HttpServletRequest request,
                                         @NotNull(message = "{NotEmpty.coverImage.image}")
                                         @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"}, message = "{ContentType.newJob.images}")
                                         @FormDataParam("coverImage") FormDataBodyPart coverImage,
                                         @PathParam("id") final long id) throws IOException {

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        LOGGER.info("Accessed /users/{}/coverImage PUT controller", id);

        InputStream in = coverImage.getEntityAs(InputStream.class);
        userService.updateCoverImage(new NewImageDto(StreamUtils.copyToByteArray(in), coverImage.getMediaType().toString()), user);
        return Response.ok().build();
    }


    @GET
    @Path("/{id}/followers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFollowers(
        @PathParam("id") final long id,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("4") int pageSize
    ) {
        LOGGER.info("Accessed /users/{}/followers GET controller page {} with pageSize {}", id, page, pageSize);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<User> results = searchService.getUserFollowers(user, page, pageSize);

        if (results == null) {
            return Response.status(BAD_REQUEST).build();
        }

        final Collection<UserDto> userDtos = UserDto.mapUserToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<Collection<UserDto>>(userDtos) {
        }, uriBuilder);
    }

    @GET
    @Path("/{id}/following")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFollowings(
        @PathParam("id") final long id,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("4") int pageSize
    ) {
        LOGGER.info("Accessed /users/{}/following GET controller page {} with pageSize {}", id, page, pageSize);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<User> results = searchService.getUserFollowing(user, page, pageSize);

        if (results == null) {
            return Response.status(BAD_REQUEST).build();
        }

        final Collection<UserDto> userDtos = UserDto.mapUserToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<Collection<UserDto>>(userDtos) {
        }, uriBuilder);
    }

    @GET
    @Path("/{id}/following/{followId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isFollowed(@PathParam("id") long id, @PathParam("followId") long followId) {
        LOGGER.info("Accessed /users/{}/following/{} GET controller", id, followId);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final User follow = userService.getUserById(followId).orElseThrow(UserNotFoundException::new);

        final boolean isFollowing = userService.isUserFollowing(user, follow);

        if (!isFollowing) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/following/{followId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response followUser(@PathParam("id") long id, @PathParam("followId") long followId) {
        LOGGER.info("Accessed /users/{}/following/{} PUT controller", id, followId);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final User toFollow = userService.getUserById(followId).orElseThrow(UserNotFoundException::new);
        userService.followUser(user, toFollow);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/following/{followId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unfollowUser(@PathParam("id") long id, @PathParam("followId") long followId) {
        LOGGER.info("Accessed /users/{}/following/{} DELETE controller", id, followId);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final User toUnfollow = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        userService.unfollowUser(user, toUnfollow);

        return Response.noContent().build();
    }


    @GET
    @Path("/{id}/receivedRequests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserJobRequests(
        @PathParam("id") final long id,
        @QueryParam("status") String status,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("order") @DefaultValue("NEWEST") String order,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /users/{}/receivedRequests GET controller", id);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        final PaginatedSearchResult<JobContact> results = searchService.getClientsByProvider(user, status, order, page, pageSize);

        if (results == null) {
            return Response.status(BAD_REQUEST).build();
        }

        final Collection<JobContactDto> contactsDto = JobContactDto.mapContactToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        if (status != null) {
            uriBuilder.queryParam("status", status);
        }

        return createPaginationResponse(results, new GenericEntity<Collection<JobContactDto>>(contactsDto) {
        }, uriBuilder);
    }

    @PUT
    @Path("/{id}/receivedRequests/{requestId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changeRequestStatus(
        @PathParam("id") final long id,
        @PathParam("requestId") final long contactId,
        final NewStatusDto status) {
        LOGGER.info("Accessed /users/{}/receivedRequests/{} PUT controller", id, contactId);

        if (status == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
        final JobStatus newStatus = status.getStatus();
//FIXME: METODO NO TIENE SENTIDO, YASE QUE SOY EL PROVIDER ACA, NUNCA SERIA EL USER
        if (!isValidStatus(jobContact, user, newStatus)) {
            throw new IllegalOperationException();
        }

        switch (newStatus) {
            case FINISHED:
                jobService.finishJob(jobContact);
                break;
            case REJECTED:
                jobService.rejectJob(jobContact);
                break;
            case IN_PROGRESS:
                jobService.acceptJob(jobContact);
                break;
            case CANCELED:
                jobService.cancelJob(jobContact);
                break;
            default:
                return Response.status(BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("/{id}/receivedRequests/{requestId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRequest(@PathParam("id") final long id,
                               @PathParam("requestId") final long contactId) {

        LOGGER.info("Accessed /users/{}/receivedRequests/{} GET controller", id, contactId);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);

        if (!user.getId().equals(jobContact.getProvider().getId()) && !user.getId().equals(jobContact.getUser().getId())) {
            throw new IllegalOperationException();
        }

        final JobContactDto jobContactDto = new JobContactDto(jobContact, uriInfo, securityContext);

        return Response.ok(jobContactDto).build();
    }

    @GET
    @Path("/{id}/sentRequests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserSentJobRequests(
        @PathParam("id") final long id,
        @QueryParam("status") String status,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("order") @DefaultValue("NEWEST") String order,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {

        LOGGER.info("Accessed /users/{}/sentRequests GET controller", id);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        final PaginatedSearchResult<JobContact> results = searchService.getProvidersByClient(user, status, order, page, pageSize);

        if (results == null) {
            return Response.status(BAD_REQUEST).build();
        }

        final Collection<JobContactDto> contactsDto = JobContactDto.mapContactToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        if (status != null) {
            uriBuilder.queryParam("status", status);
        }

        return createPaginationResponse(results, new GenericEntity<Collection<JobContactDto>>(contactsDto) {
        }, uriBuilder);
    }

    @GET
    @Path("/{id}/jobs")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserJobs(
        @PathParam("id") final long id,
        @QueryParam("query") @DefaultValue("") String query,
        @QueryParam("order") @DefaultValue("MOST_POPULAR") String order,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /user/jobs GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        final PaginatedSearchResult<Job> results = searchService.getJobsByProvider(query, order, user, page, pageSize);

        if (results == null) {
            return Response.status(BAD_REQUEST).build();
        }

        final Collection<JobDto> jobsDto = JobDto.mapJobToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize)
            .queryParam("order", order)
            .queryParam("query", query);

        return createPaginationResponse(results, new GenericEntity<Collection<JobDto>>(jobsDto) {
        }, uriBuilder);
    }


    @GET
    @Path("/{id}/contactInfo")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContactInfo(@PathParam("id") final long id) {
        LOGGER.info("Accessed /users/{id}/contactInfo GET controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final Collection<ContactInfoDto> contactInfoCollection = ContactInfoDto.mapCollectionInfoToDto(user.getContactInfo());

        if (contactInfoCollection.isEmpty()) {
            return Response.status(NO_CONTENT.getStatusCode()).build();
        }

        GenericEntity<Collection<ContactInfoDto>> entity = new GenericEntity<Collection<ContactInfoDto>>(contactInfoCollection) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotificationsByUser(
        @PathParam("id") final long id,
        @QueryParam("onlyNew") @DefaultValue("false") boolean onlyNew,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /users/{}/notifications GET controller", id);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        assureUserResourceCorrelation(user, id);

        final PaginatedSearchResult<Notification> results = searchService.getNotificationsByUser(user, onlyNew, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<NotificationDto> notificationsDtos = NotificationDto.MapNotificationToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<Collection<NotificationDto>>(notificationsDtos) {
        }, uriBuilder);
    }


    @PUT
    @Path("/{id}/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response markAllNotificationsAsSeen(@PathParam("id") final long id) {
        LOGGER.info("Accessed /users/{}/notifications PUT controller", id);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        notificationService.markAllNotificationsAsSeen(user);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/notifications/{notificationId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotification(@PathParam("id") final long id, @PathParam("notificationId") long notificationId) {
        LOGGER.info("Accessed /users/{}/notifications/{} GET controller", id, notificationId);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        Notification notification = notificationService.getNotificationById(notificationId).orElseThrow(NotificationNotFoundException::new);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalOperationException();
        }
        return Response.ok(new NotificationDto(notification, uriInfo, securityContext)).build();
    }

    @PUT
    @Path("/{id}/notifications/{notificationId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response markNotificationAsSeen(@PathParam("id") final long id, @PathParam("notificationId") long notificationId) {
        LOGGER.info("Accessed /users/{}/notifications/{} PUT controller", id, notificationId);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        Notification notification = notificationService.getNotificationById(notificationId).orElseThrow(NotificationNotFoundException::new);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalOperationException();
        }
        notificationService.markNotificationAsSeen(notification);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/unseenNotifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUnseenNotificationsByUser(@PathParam("id") final long id) {
        LOGGER.info("Accessed /users/{}/unseenNotifications GET controller", id);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        assureUserResourceCorrelation(user, id);

        final int count = notificationService.getUnseenNotificationsCount(user);

        return Response.ok(new NotificationsCountDto(count)).build();
    }


    private <T, K> Response createPaginationResponse(PaginatedSearchResult<T> results,
                                                     GenericEntity<Collection<K>> resultsDto,
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

    private void assureUserResourceCorrelation(User user, long userId) {
        if (user.getId() != userId) {
            throw new ForbiddenException();
        }
    }

    //    FIXME: RARO, SI OSN SENT REQUESTS YASE Q TENGO Q SER EL USER SINO SOY EL PROVIDER
    private boolean isValidStatus(JobContact jobContact, User user, JobStatus newStatus) {
        JobStatus actualStatus = jobContact.getStatus();
        if (user.getId().equals(jobContact.getUser().getId())) {
            return newStatus.equals(CANCELED) && !newStatus.equals(actualStatus);
        } else if (user.getId().equals(jobContact.getProvider().getId())) {
            if (actualStatus.equals(PENDING) && (newStatus.equals(IN_PROGRESS) || newStatus.equals(REJECTED))) {
                return true;
            } else return actualStatus.equals(IN_PROGRESS) && newStatus.equals(FINISHED);
        }
        return false;
    }

}