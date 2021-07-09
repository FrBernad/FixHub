package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ServerInternalException;
import ar.edu.itba.paw.interfaces.exceptions.StateNotFoundException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.image.ImageDto;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response registerUser(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        return Response.ok(null).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        return Response.ok(new UserDto(user)).build();
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
//    @RequestMapping(path = "/user/account/updateProviderStateAndTime")
//    public ModelAndView firstUpdateProvider(@ModelAttribute("providerInfoFirstForm") FirstJoinForm form, Principal principal) {
//        LOGGER.info("Accessed /user/account/updateProviderInfo GET controller");
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if(!user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is not a provider", user.getId());
//            return new ModelAndView("redirect:/user/account");
//        }
//        ModelAndView mav = new ModelAndView("views/user/editProviderInfo");
//        mav.addObject("states", locationService.getStates());
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/account/updateProviderStateAndTime", method = RequestMethod.POST)
//    public ModelAndView firstUpdateProviderPost(@Valid @ModelAttribute("providerInfoFirstForm") FirstJoinForm form, BindingResult errors, RedirectAttributes ra, Principal principal) {
//        LOGGER.info("Accessed /user/account/updateProviderInfo POST controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if(!user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is not a provider", user.getId());
//            return new ModelAndView("redirect:/user/account");
//        }
//        if(errors.hasErrors()) {
//            LOGGER.warn("Error in form providerInfoForm data");
//            return firstUpdateProvider(form, principal);
//        }
//
//
//        ra.addFlashAttribute("state", form.getState());
//        ra.addFlashAttribute("startTime", form.getStartTime());
//        ra.addFlashAttribute("endTime", form.getEndTime());
//        final State state = locationService.getStateById(form.getState()).orElseThrow(StateNotFoundException::new);
//        ra.addFlashAttribute("cities", locationService.getCitiesByState(state));
//
//        LOGGER.debug("Added redirect attributes to /user/account/updateProviderCity");
//
//        return new ModelAndView("redirect:/user/account/updateProviderCity");
//
//    }
//
//    @RequestMapping(path = "/user/account/updateProviderCity")
//    public ModelAndView secondUpdateProvider(@ModelAttribute("providerInfoSecondForm") SecondJoinForm form, HttpServletRequest request, Principal principal) {
//        LOGGER.info("Accessed /user/account/updateProviderCity GET controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if(!user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is not a provider", user.getId());
//            return new ModelAndView("redirect:/user/account");
//        }
//
//        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//        if (flashMap == null && form.getState() == 0) {
//            LOGGER.warn("Flashmap and form are null redirecting to first step");
//            return new ModelAndView("redirect:/user/account/updateProviderStateAndTime");
//        }
//
//        ModelAndView mav = new ModelAndView("views/user/editProviderCity");
//
//        boolean newState = false;
//
//        if (flashMap != null) {
//            mav.addAllObjects(flashMap);
//            newState = user.getProviderDetails().getLocation().getState().getId() != (Long)flashMap.get("state");
//        }
//
//        if (form != null && form.getState() != 0) {
//            LOGGER.debug("Adding state cities again due to error in firstUpdateProvider form");
//            final State state = locationService.getStateById(form.getState()).orElseThrow(StateNotFoundException::new);
//            mav.addObject("cities", locationService.getCitiesByState(state));
//            newState = user.getProviderDetails().getLocation().getState().getId() != form.getState();
//        }
//
//        mav.addObject("newState", newState);
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/account/updateProviderCity", method = RequestMethod.POST)
//    public ModelAndView secondUpdateProviderPost(@Valid @ModelAttribute("providerInfoSecondForm") final SecondJoinForm form,
//                                                 final BindingResult errors,
//                                                 HttpServletRequest request,
//                                                 Principal principal) {
//        LOGGER.info("Accessed /user/account/updateProviderCity POST controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if (!user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is not a provider", user.getId());
//            return new ModelAndView("redirect:/user/account");
//        }
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form SecondJoinForm data");
//            return secondUpdateProvider(form, request, principal);
//        }
//
//        userService.updateProviderInfo(user, form.getCity(), form.getStartTime(), form.getEndTime());
//
//        return new ModelAndView("redirect:/user/dashboard");
//    }
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
//    @RequestMapping(path = "/user/account/updateInfo")
//    public ModelAndView updateProfile(@ModelAttribute("userInfoForm") UserInfoDto form) {
//        LOGGER.info("Accessed /user/account/updateInfo GET controller");
//        return new ModelAndView("views/user/profile/editProfile");
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

}