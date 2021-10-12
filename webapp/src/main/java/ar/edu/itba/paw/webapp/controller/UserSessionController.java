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

//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userAuthDto.getEmail(), userAuthDto.getPassword())
//            );
//
//            final User user = userService.getUserByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
//
            final Response.ResponseBuilder responseBuilder = Response.noContent();
//
//            addAuthorizationHeader(responseBuilder, user);
//
//            addSessionRefreshTokenCookie(responseBuilder, user);

            return responseBuilder.build();

//        } catch (AuthenticationException e) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
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
    @Path("/refreshToken")
    public Response refreshAccessToken(@CookieParam(JwtUtil.SESSION_REFRESH_TOKEN_COOKIE_NAME) String sessionRefreshToken) {
        LOGGER.info("Accessed /user/refreshToken POST controller");

//        if (sessionRefreshToken == null) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//
//        final Optional<User> userOpt = userService.getUserByRefreshToken(sessionRefreshToken);
//
//        if (!userOpt.isPresent()) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//
//        final User user = userOpt.get();

        final Response.ResponseBuilder responseBuilder = Response.noContent();

//        addAuthorizationHeader(responseBuilder, user);

        return responseBuilder.build();
    }

    @Produces
    @DELETE
    @Path("/refreshToken")
    public Response deleteRefreshToken(@CookieParam(JwtUtil.SESSION_REFRESH_TOKEN_COOKIE_NAME) Cookie sessionRefreshCookie,
                                       @QueryParam("allSessions") @DefaultValue("false") boolean allSessions) {

        LOGGER.info("Accessed /user/refreshToken DELETE controller");

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


    private void addAuthorizationHeader(Response.ResponseBuilder responseBuilder, User user) {
        responseBuilder.header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user, uriInfo));
    }

    private void addSessionRefreshTokenCookie(Response.ResponseBuilder responseBuilder, User user) {
        responseBuilder.cookie(jwtUtil.generateSessionRefreshCookie(userService.getSessionRefreshToken(user)));
    }

}
