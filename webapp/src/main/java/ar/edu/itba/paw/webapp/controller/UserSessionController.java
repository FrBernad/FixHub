package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
import ar.edu.itba.paw.interfaces.exceptions.NotificationNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.NotificationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.models.user.notification.Notification;
import ar.edu.itba.paw.webapp.auth.JwtUtil;
import ar.edu.itba.paw.webapp.dto.request.JoinDto;
import ar.edu.itba.paw.webapp.dto.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Component
public class UserSessionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


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






}
