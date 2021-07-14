package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.models.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.Optional;

@Path("/users")
@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LocationService locationService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerUser(@Valid final RegisterDto registerDto) {
        User user;
        try {
            user = userService.createUser(registerDto.getPassword(),
                registerDto.getName(), registerDto.getSurname(),
                registerDto.getEmail(), registerDto.getPhoneNumber(),
                registerDto.getState(), registerDto.getCity());
        } catch (DuplicateUserException e) {
            LOGGER.warn("Error in registerDto RegisterForm, email is already in used");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.created(UserDto.getUserUriBuilder(user, uriInfo).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        if (user.getRoles().contains(Roles.PROVIDER)) {
            return Response.ok(new ProviderDto(user, uriInfo, securityContext)).build();
        }

        return Response.ok(new UserDto(user, uriInfo, securityContext)).build();
    }

    @GET
    @Path("/{id}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final Image img = user.getProfileImage();

        if (img == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final byte[] profileImage = img.getData();

        return Response.ok(profileImage).type(img.getMimeType()).build();
    }

    @GET
    @Path("/{id}/coverImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserCoverImage(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final Image img = user.getCoverImage();

        if (img == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final byte[] coverImage = img.getData();

        return Response.ok(coverImage).type(img.getMimeType()).build();
    }

    @GET
    @Path("/{id}/followers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserFollowers(
        @PathParam("id") final long id,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("4") int pageSize
    ) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<User> results = searchService.getUserFollowers(user, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<UserDto> userDtos = UserDto.mapUserToDto(results.getResults(), uriInfo, securityContext);

        final PaginatedResultDto<UserDto> resultsDto =
            new PaginatedResultDto<>(
                results.getPage(),
                results.getTotalPages(),
                userDtos);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<UserDto>>(resultsDto) {
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

        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<User> results = searchService.getUserFollowing(user, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<UserDto> userDtos = UserDto.mapUserToDto(results.getResults(), uriInfo, securityContext);

        final PaginatedResultDto<UserDto> resultsDto =
            new PaginatedResultDto<>(
                results.getPage(),
                results.getTotalPages(),
                userDtos);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<UserDto>>(resultsDto) {
        }, uriBuilder);
    }


//    @RequestMapping(path = "/user/account/search")
//    public ModelAndView profileSearch(@ModelAttribute("searchForm") final SearchForm form,
//                                      BindingResult errors, Principal principal) {
//        LOGGER.info("Accessed /user/account/search GET controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClient(user, form.getPage(), 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
//        mav.addObject("results", providersContacted);
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/follow", method = RequestMethod.POST)
//    public ModelAndView follow(@RequestParam("userId") long userId, Principal principal) {
//        LOGGER.info("Accessed /user/follow POST controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final User followUser = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        if (user.getId() != userId) {
//            userService.followUser(user, followUser);
//        }
//        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
//        mav.addObject("loggedUser", user);
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/unfollow", method = RequestMethod.POST)
//    public ModelAndView unfollow(@RequestParam("userId") long userId, Principal principal) {
//        LOGGER.info("Accessed /user/unfollow POST controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        final User followUser = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//
//        if (user.getId() != userId) {
//            userService.unfollowUser(user, followUser);
//        }
//        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
//        mav.addObject("loggedUser", user);
//        return mav;
//    }
//
//
//    @RequestMapping(path = "/user/{userId}/followers")
//    public ModelAndView followers(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
//        LOGGER.info("Accessed /user/profile/followers GET controller");
//
//        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user, 0, 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
//        mav.addObject("results", followers);
//        mav.addObject("user", user);
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/{userId}/followers/search")
//    public ModelAndView followersSearch(@PathVariable("userId") long userId,
//                                        @ModelAttribute("searchForm") SearchForm form,
//                                        BindingResult errors) {
//        LOGGER.info("Accessed /user/profile/followers/search GET controller");
//
//        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
////        user.setFollowers(userService.getFollowersCount(user));
////        user.setFollowing(userService.getFollowingCount(user));
//        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user, form.getPage(), 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
//        mav.addObject("results", followers);
//        mav.addObject("user", user);
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/{userId}/following")
//    public ModelAndView following(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
//        LOGGER.info("Accessed /user/profile/following GET controller");
//
//        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
////        user.setFollowers(userService.getFollowersCount(user));
////        user.setFollowing(userService.getFollowingCount(user));
//        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user, 0, 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
//        mav.addObject("flag", true);
//        mav.addObject("results", following);
//        mav.addObject("user", user);
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/{userId}/following/search")
//    public ModelAndView followingSearch(@PathVariable("userId") long userId,
//                                        @ModelAttribute("searchForm") SearchForm form,
//                                        BindingResult errors) {
//        LOGGER.info("Accessed /user/profile/following/search GET controller");
//
//        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
////        user.setFollowers(userService.getFollowersCount(user));
////        user.setFollowing(userService.getFollowingCount(user));
//        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user, form.getPage(), 4);
//        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
//        mav.addObject("results", following);
//        mav.addObject("flag", true);
//        mav.addObject("user", user);
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/{userId}")
//    public ModelAndView userProfile(@PathVariable("userId") final long userId, Principal principal) {
//
//        LOGGER.info("Accessed /user/{} GET controller", userId);
//        ModelAndView mav = new ModelAndView("views/user/profile/otherProfile");
//        if (principal != null) {
//            User loggedUser = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//            if (loggedUser.getId() == userId) {
//                LOGGER.debug("Redirecting to /user/account");
//                return new ModelAndView("redirect:/user/account");
//            }
//            boolean followed = loggedUser.getFollowing().stream().anyMatch(user -> user.getId() == userId);
//            mav.addObject("loggedUser", loggedUser);
//            mav.addObject("followed", followed);
//        }
//        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        mav.addObject("user", user);
//        return mav;
//    }
//

//
//    @RequestMapping(path = "/user/account")
//    public ModelAndView profile(@ModelAttribute("searchForm") SearchForm searchForm, Principal principal) {
//        LOGGER.info("Accessed /user/account GET controller");
//
//        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClient(user, 0, 4);
//
//        mav.addObject("loggedUser", user);
//        mav.addObject("results", providersContacted);
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/account/updateCoverImage", method = RequestMethod.POST)
//    public ModelAndView updateCoverImage(@Valid @ModelAttribute("coverImageForm")CoverImageForm coverImageForm,
//                                         BindingResult errors,
//                                         Principal principal,
//                                         RedirectAttributes ra) {
//
//        LOGGER.info("Accessed /user/account/updateCoverImage POST controller");
//        ModelAndView mav = new ModelAndView("redirect:/user/account");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        if(errors.hasErrors()){
//            LOGGER.warn("Update cover image form is not valid");
//            ra.addFlashAttribute("coverImageForm", coverImageForm);
//            ra.addFlashAttribute("org.springframework.validation.BindingResult.coverImageForm", errors);
//            return mav;
//        }
//        try {
//            userService.updateCoverImage(new ImageDto(coverImageForm.getCoverImage().getBytes(), coverImageForm.getCoverImage().getContentType()), user);
//        } catch (IOException e) {
//            LOGGER.warn("Error accessing file bytes");
//            throw new ServerInternalException();
//        }
//        return mav;
//    }
//
//
//    @RequestMapping(value = "/user/account/updateProfileImage", method = RequestMethod.POST)
//    public ModelAndView updateProfileImage(@Valid @ModelAttribute("profileImageForm") ProfileImageForm profileImageForm,
//                                           BindingResult errors,
//                                           Principal principal,
//                                           RedirectAttributes ra) {
//        LOGGER.info("Accessed /user/account/updateProfileImage POST controller");
//        ModelAndView mav = new ModelAndView("redirect:/user/account");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        if(errors.hasErrors()){
//            LOGGER.warn("Update profile image form is not valid");
//            ra.addFlashAttribute("org.springframework.validation.BindingResult.profileImageForm", errors);
//            ra.addFlashAttribute("profileImageForm", profileImageForm);
//            return mav;
//        }
//        try {
//            userService.updateProfileImage(new ImageDto(profileImageForm.getProfileImage().getBytes(), profileImageForm.getProfileImage().getContentType()), user);
//        } catch (IOException e) {
//            LOGGER.warn("Error accessing file bytes");
//            throw new ServerInternalException();
//        }
//        return mav;
//    }
//


//    @RequestMapping(value = "/user/account/updateInfo", method = RequestMethod.POST)
//    public ModelAndView updateProfileInfo(@Valid @ModelAttribute("userInfoForm") final UserInfoDto form,
//                                          BindingResult errors, Principal principal) {
//        LOGGER.info("Accessed /user/account/updateInfo POST controller");
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form UserInfoDto data");
//            return updateProfile(form);
//        }
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        userService.updateUserInfo(
//            new UserInfo(form.getName(), form.getSurname(),
//                form.getCity(), form.getState(), form.getPhoneNumber()),
//            user);
//
//        return new ModelAndView("redirect:/user/account");
//    }


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