package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ServerInternalException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContentTypeException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ImageService imageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/account")
    public ModelAndView profile(@ModelAttribute("searchForm") final SearchForm form, Principal principal) {
        LOGGER.info("Accessed /user/account GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClientId(user.getId(), 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
        mav.addObject("loggedUser", user);
        mav.addObject("results", providersContacted);
        return mav;
    }

    @RequestMapping(path = "/user/account/search")
    public ModelAndView profileSearch(@ModelAttribute("searchForm") final SearchForm form,
                                      BindingResult errors, Principal principal) {
        LOGGER.info("Accessed /user/account/search GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClientId(user.getId(), form.getPage(), 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
        mav.addObject("results", providersContacted);
        return mav;
    }

    @RequestMapping(path = "/user/follow", method = RequestMethod.POST)
    public ModelAndView follow(@RequestParam("userId") long userId, Principal principal) {
        LOGGER.info("Accessed /user/follow POST controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.getId() != userId) {
            userService.followUserById(user.getId(), userId);
        }
        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
        mav.addObject("loggedUser", user);
        return mav;
    }

    @RequestMapping(path = "/user/unfollow", method = RequestMethod.POST)
    public ModelAndView unfollow(@RequestParam("userId") long userId, Principal principal) {
        LOGGER.info("Accessed /user/unfollow POST controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (user.getId() != userId) {
            userService.unfollowUserById(user.getId(), userId);
        }
        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
        mav.addObject("loggedUser", user);
        return mav;
    }


    @RequestMapping(path = "/user/{userId}/followers")
    public ModelAndView followers(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
        LOGGER.info("Accessed /user/profile/followers GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user.getId(), 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("results", followers);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/followers/search")
    public ModelAndView followersSearch(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form, BindingResult errors) {
        LOGGER.info("Accessed /user/profile/followers/search GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user.getId(), form.getPage(), 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("results", followers);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/following")
    public ModelAndView following(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
        LOGGER.info("Accessed /user/profile/following GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user.getId(), 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("flag", true);
        mav.addObject("results", following);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/following/search")
    public ModelAndView followingSearch(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form, BindingResult errors) {
        LOGGER.info("Accessed /user/profile/following/search GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user.getId(), form.getPage(), 4);
        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("results", following);
        mav.addObject("flag", true);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId, Principal principal) {

        LOGGER.info("Accessed /user/{} GET controller", userId);
        ModelAndView mav = new ModelAndView("views/user/profile/otherProfile");
        if (principal != null) {
            User loggedUser = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
            if (loggedUser.getId() == userId) {
                LOGGER.debug("Redirecting to /user/account");
                return new ModelAndView("redirect:/user/account");
            }
            boolean followed = userService.getAllUserFollowingsIds(loggedUser.getId()).stream().anyMatch(id -> id == userId);
            mav.addObject("loggedUser", loggedUser);
            mav.addObject("followed", followed);
        }
        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user.getId()));
//        user.setFollowing(userService.getFollowingCount(user.getId()));
        mav.addObject("user", user);
        return mav;
    }


    @RequestMapping(path = "/user/account/updateInfo")
    public ModelAndView updateProfile(@ModelAttribute("userInfoForm") UserInfoForm form) {
        LOGGER.info("Accessed /user/account/updateInfo GET controller");
        return new ModelAndView("views/user/profile/editProfile");
    }

    @RequestMapping(value = "/user/account/updateInfo", method = RequestMethod.POST)
    public ModelAndView updateProfileInfo(@Valid @ModelAttribute("userInfoForm") final UserInfoForm form,
                                          BindingResult errors, Principal principal) {
        LOGGER.info("Accessed /user/account/updateInfo POST controller");

        if (errors.hasErrors()) {
            LOGGER.warn("Error in form UserInfoForm data");
            return updateProfile(form);
        }

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        userService.updateUserInfo(
            new UserInfo(form.getName(), form.getSurname(),
                form.getCity(), form.getState(), form.getPhoneNumber()),
            user);

        return new ModelAndView("redirect:/user/account");
    }

    @RequestMapping(value = "/user/account/updateCoverImage", method = RequestMethod.POST)
    public ModelAndView updateCoverImage(@RequestParam("image") MultipartFile file,
                                         Principal principal) {
        LOGGER.info("Accessed /user/account/updateCoverImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypesNoGIF().contains(file.getContentType())) {
            LOGGER.warn("Image content type is not valid");
            throw new IllegalContentTypeException();
        }
        try {
            userService.updateCoverImage(new ImageDto(file.getBytes(), file.getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new ServerInternalException();
        }
        return new ModelAndView("redirect:/user/account");
    }


    @RequestMapping(value = "/user/account/updateProfileImage", method = RequestMethod.POST)
    public ModelAndView updateProfileImage(@RequestParam("image") MultipartFile file,
                                           Principal principal) {
        LOGGER.info("Accessed /user/account/updateProfileImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypesGIF().contains(file.getContentType())) {
            LOGGER.warn("Image content type is not valid");
            throw new IllegalContentTypeException();
        }
        try {
            userService.updateProfileImage(new ImageDto(file.getBytes(), file.getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new ServerInternalException();
        }
        return new ModelAndView("redirect:/user/account");
    }

}