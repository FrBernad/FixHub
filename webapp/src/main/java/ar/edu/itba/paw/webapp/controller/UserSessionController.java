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
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.webapp.auth.JwtUtil;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import ar.edu.itba.paw.webapp.dto.request.JoinDto;
import ar.edu.itba.paw.webapp.dto.request.NewStatusDto;
import ar.edu.itba.paw.webapp.dto.response.*;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.models.job.JobStatus.*;

@Path("/user")
@Component
public class UserSessionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private int maxRequestSize;

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private JobService jobService;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response loginUser(UserAuthDto userAuthDto) {
        LOGGER.info("Accessed /user POST controller");

        if (userAuthDto == null) {
            throw new ContentExpectedException();
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDto.getEmail(), userAuthDto.getPassword())
            );

            final User user = userService.getUserByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

            final Response.ResponseBuilder responseBuilder = Response.noContent();

            addAuthorizationHeader(responseBuilder, user);

            addSessionRefreshTokenCookie(responseBuilder, user);

            return responseBuilder.build();

        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUser(@Valid UserInfoDto userInfoDto) {
        LOGGER.info("Accessed /user/ PUT controller");

        if (userInfoDto == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        userService.updateUserInfo(
            new UserInfo(userInfoDto.getName(), userInfoDto.getSurname(),
                userInfoDto.getCity(), userInfoDto.getState(), userInfoDto.getPhoneNumber()),
            user);

        return Response.ok().build();
    }


    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser() {
        LOGGER.info("Accessed /user/ GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        LOGGER.info("Return user with id {} in /user/ GET controller", user.getId());

        return Response.ok(new SessionUserDto(user, uriInfo, securityContext)).build();

    }

    @Produces
    @POST
    @Path("/refreshSession")
    public Response refreshAccessToken(@CookieParam(JwtUtil.SESSION_REFRESH_TOKEN_COOKIE_NAME) String sessionRefreshToken) {
        LOGGER.info("Accessed /user/refreshSession POST controller");

        if (sessionRefreshToken == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        final Optional<User> userOpt = userService.getUserByRefreshToken(sessionRefreshToken);

        if (!userOpt.isPresent()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        final User user = userOpt.get();

        final Response.ResponseBuilder responseBuilder = Response.noContent();

        addAuthorizationHeader(responseBuilder, user);

        return responseBuilder.build();
    }

    @Produces
    @DELETE
    @Path("/refreshSession")
    public Response deleteRefreshToken(@CookieParam(JwtUtil.SESSION_REFRESH_TOKEN_COOKIE_NAME) Cookie sessionRefreshCookie,
                                       @QueryParam("allSessions") @DefaultValue("false") boolean allSessions) {

        LOGGER.info("Accessed /user/refreshSession DELETE controller");

        final Response.ResponseBuilder responseBuilder = Response.noContent();

        if (sessionRefreshCookie != null) {
            responseBuilder.cookie(jwtUtil.generateDeleteSessionCookie());

            if (allSessions) {
                userService
                    .getUserByRefreshToken(sessionRefreshCookie.getValue())
                    .ifPresent(user -> userService.deleteSessionRefreshToken(user));
            }
        }

        return responseBuilder.build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/verify")
    public Response verifyUser(TokenDto tokenDto) {
        LOGGER.info("Accessed /user/verify PUT controller");

        if (tokenDto == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.verifyAccount(tokenDto.getToken()).orElseThrow(UserNotFoundException::new);

        final Response.ResponseBuilder responseBuilder = Response.noContent();

        if (user.isVerified()) {
            addAuthorizationHeader(responseBuilder, user);
            if (securityContext.getUserPrincipal() == null) {
                addSessionRefreshTokenCookie(responseBuilder, user);
            }
        }
        return responseBuilder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Path("/verify")
    public Response resendUserVerification() {
        LOGGER.info("Accessed /user/verify POST controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        userService.resendVerificationToken(user);
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/resetPassword")
    public Response sendResetPasswordEmail(@Valid final PasswordResetEmailDto passwordResetDto) {
        LOGGER.info("Accessed /user/resetPassword POST controller");

        if (passwordResetDto == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.getUserByEmail(passwordResetDto.getEmail()).orElseThrow(UserNotFoundException::new);

        userService.generateNewPassword(user);

        return Response.noContent().build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/resetPassword")
    public Response resetPassword(@Valid final PasswordResetDto passwordResetDto) {
        LOGGER.info("Accessed /user/resetPassword PUT controller");

        if (passwordResetDto == null) {
            throw new ContentExpectedException();
        }

        userService.updatePassword(passwordResetDto.getToken(), passwordResetDto.getPassword()).orElseThrow(UserNotFoundException::new);

        return Response.noContent().build();
    }

    @GET
    @Path("/coverImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUserCoverImage(@Context Request request) {
        LOGGER.info("Accessed /user/coverImage GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final Image img = user.getProfileImage();

        if (img == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
    @Path("/coverImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserCoverImage(@Context final HttpServletRequest request,
                                         @NotNull(message = "{NotEmpty.coverImage.image}") @ImageTypeConstraint(contentType = {"image/png", "image/jpeg"}, message = "{ContentType.newJob.images}") @FormDataParam("coverImage") FormDataBodyPart coverImage) throws IOException {

        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }

        LOGGER.info("Accessed /user/coverImage PUT controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        InputStream in = coverImage.getEntityAs(InputStream.class);
        userService.updateCoverImage(new NewImageDto(StreamUtils.copyToByteArray(in), coverImage.getMediaType().toString()), user);
        return Response.ok().build();
    }

    @GET
    @Path("/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage(@Context Request request) {
        LOGGER.info("Accessed /user/profileImage GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final Image img = user.getProfileImage();

        if (img == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
    @Path("/profileImage")
    public Response updateUserProfileImage(@Context final HttpServletRequest request,
                                           @NotNull(message = "{NotEmpty.profileImage.image}") @ImageTypeConstraint(contentType = {"image/png", "image/jpeg", "image/gif"}, message = "{ContentType.newJob.images}") @FormDataParam("profileImage") FormDataBodyPart profileImage) throws IOException {
        LOGGER.info("Accessed /user/profileImage PUT controller");
        if (request.getContentLength() == -1 || request.getContentLength() > maxRequestSize) {
            throw new MaxUploadSizeRequestException();
        }
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        InputStream in = profileImage.getEntityAs(InputStream.class);
        userService.updateProfileImage(new NewImageDto(StreamUtils.copyToByteArray(in), profileImage.getMediaType().toString()), user);
        return Response.ok().build();
    }

    @GET
    @Path("/jobs/receivedRequests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserJobRequests(
        @QueryParam("status") String status,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("order") @DefaultValue("NEWEST") String order,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /user/jobs/receivedRequests GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> results = searchService.getClientsByProvider(user, status, order, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
    @Path("/jobs/receivedRequests/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changeRequestStatus(@PathParam("id") final long contactId, final NewStatusDto status) {
        LOGGER.info("Accessed /user/jobs/receivedRequests/{} PUT controller", contactId);

        if (status == null) {
            throw new ContentExpectedException();
        }

        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final JobStatus newStatus = status.getStatus();


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
                return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("/jobs/receivedRequests/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRequest(@PathParam("id") final long contactId) {

        LOGGER.info("Accessed /user/jobs/receivedRequests/{} GET controller", contactId);

        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        if (!user.getId().equals(jobContact.getProvider().getId()) && !user.getId().equals(jobContact.getUser().getId())) {
            throw new IllegalOperationException();
        }

        final JobContactDto jobContactDto = new JobContactDto(jobContact, uriInfo, securityContext);

        return Response.ok(jobContactDto).build();
    }

    @GET
    @Path("/jobs/sentRequests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserSentJobRequests(
        @QueryParam("status") String status,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("order") @DefaultValue("NEWEST") String order,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {

        LOGGER.info("Accessed /user/jobs/sentRequests GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> results = searchService.getProvidersByClient(user, status, order, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
    @Path("/jobs")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserJobs(
        @QueryParam("query") @DefaultValue("") String query,
        @QueryParam("order") @DefaultValue("MOST_POPULAR") String order,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /user/jobs GET controller");

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> results = searchService.getJobsByProvider(query, order, user, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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

    @POST
    @Path("/account/provider")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response join(@Valid final JoinDto joinDto) {
        LOGGER.info("Accessed /user/account/provider POST controller");

        if (joinDto == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        if (user.hasRole(Roles.PROVIDER)) {
            LOGGER.warn("User with id {} is already a provider", user.getId());
            throw new IllegalOperationException();
        }

        List<Long> citiesId = new ArrayList<>();
        for (CityDto city : joinDto.getLocation().getCities()) {
            citiesId.add(city.getId());
        }

        userService.makeProvider(user,
            citiesId,
            joinDto.getSchedule().getStartTime(),
            joinDto.getSchedule().getEndTime());

        LOGGER.info("User with id {} become provider successfully", user.getId());

        final Response.ResponseBuilder responseBuilder = Response.noContent();
        addAuthorizationHeader(responseBuilder, user);

        return responseBuilder.build();
    }


    @PUT
    @Path("/account/provider")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response updateProviderInfo(final JoinDto joinDto) {
        LOGGER.info("Accessed /user/account/provider PUT controller");

        if (joinDto == null) {
            throw new ContentExpectedException();
        }

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);


        if (!user.hasRole(Roles.PROVIDER)) {
            LOGGER.warn("User {} is not a provider", user.getId());
            throw new IllegalOperationException();
        }

        List<Long> citiesId = new ArrayList<>();
        for (CityDto city : joinDto.getLocation().getCities()) {
            citiesId.add(city.getId());
        }

        userService.updateProviderInfo(user, citiesId,
            joinDto.getSchedule().getStartTime(), joinDto.getSchedule().getEndTime());

        LOGGER.info("User with id {} update provider information succesfully", user.getId());

        return Response.ok().build();

    }

    @PUT
    @Path("/following/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response followUser(@PathParam("id") long id) {
        LOGGER.info("Accessed /user/following/{} PUT controller", id);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final User toFollow = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        if (user.getId().equals(toFollow.getId())) {
            throw new IllegalOperationException();
        }
        userService.followUser(user, toFollow);


        return Response.noContent().build();
    }

    @DELETE
    @Path("/following/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unfollowUser(@PathParam("id") long id) {
        LOGGER.info("Accessed /user/following/{} DELETE controller", id);

        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        final User toUnfollow = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        if (user.getId().equals(toUnfollow.getId())) {
            throw new IllegalOperationException();
        }
        userService.unfollowUser(user, toUnfollow);

        return Response.noContent().build();
    }

    @GET
    @Path("/contactInfo")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContactInfo() {
        LOGGER.info("Accessed /user/contactInfo GET controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);


        final Collection<ContactInfoDto> contactInfoCollection = ContactInfoDto.mapCollectionInfoToDto(user.getContactInfo());

        if (contactInfoCollection.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }

        GenericEntity<Collection<ContactInfoDto>> entity = new GenericEntity<Collection<ContactInfoDto>>(contactInfoCollection) {
        };

        return Response.ok(entity).build();
    }

    private void addAuthorizationHeader(Response.ResponseBuilder responseBuilder, User user) {
        responseBuilder.header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user));
    }

    private void addSessionRefreshTokenCookie(Response.ResponseBuilder responseBuilder, User user) {
        responseBuilder.cookie(jwtUtil.generateSessionRefreshCookie(userService.getSessionRefreshToken(user)));
    }

    private <T, K> Response createPaginationResponse(PaginatedSearchResult<T> results,
                                                     GenericEntity<Collection<K>> resultsDto,
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

    @GET
    @Path("/notifications/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotification(@PathParam("id") long id) {
        LOGGER.info("Accessed /user/notifications/{} GET controller", id);
        Notification notification = notificationService.getNotificationById(id).orElseThrow(NotificationNotFoundException::new);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalOperationException();
        }

        return Response.ok(new NotificationDto(notification, uriInfo, securityContext)).build();
    }

    @PUT
    @Path("/notifications/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response markNotificationAsSeen(@PathParam("id") long id) {
        LOGGER.info("Accessed /user/notifications/{} PUT controller", id);
        Notification notification = notificationService.getNotificationById(id).orElseThrow(NotificationNotFoundException::new);
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalOperationException();
        }
        notificationService.markNotificationAsSeen(notification);
        return Response.ok().build();
    }


    @GET
    @Path("/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getNotificationsByUser(
        @QueryParam("onlyNew") @DefaultValue("false") boolean onlyNew,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        LOGGER.info("Accessed /user/notifications GET controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

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

    @GET
    @Path("/unseenNotifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUnseenNotificationsByUser(
    ) {
        LOGGER.info("Accessed /user/unseenNotiffications GET controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);

        final int count = notificationService.getUnseenNotificationsCount(user);

        return Response.ok(new NotificationsCountDto(count)).build();
    }

    @PUT
    @Path("/notifications")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response markAllNotificationsAsSeen() {
        LOGGER.info("Accessed /user/notifications PUT controller");
        final User user = userService.getUserByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        notificationService.markAllNotificationsAsSeen(user);
        return Response.ok().build();
    }

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
