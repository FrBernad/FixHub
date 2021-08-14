package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ContentExpectedException;
import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.response.PaginatedResultDto;
import ar.edu.itba.paw.webapp.dto.response.RegisterDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/users")
@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerUser(@Valid final RegisterDto registerDto) throws DuplicateUserException {
        LOGGER.info("Accessed /users/ POST controller");
        if(registerDto == null) {
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

    @GET
    @Path("/{id}/coverImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserCoverImage(@PathParam("id") final long id, @Context Request request) {
        LOGGER.info("Accessed /users/{}/coverImage GET controller", id);

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final Image img = user.getCoverImage();

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
            return Response.status(Response.Status.BAD_REQUEST).build();
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
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<UserDto> userDtos = UserDto.mapUserToDto(results.getResults(), uriInfo, securityContext);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<Collection<UserDto>>(userDtos) {
        }, uriBuilder);
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