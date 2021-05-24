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
    public ModelAndView profile(@ModelAttribute("searchForm") SearchForm searchForm,
                                @ModelAttribute("coverImageForm") CoverImageForm coverImageForm,
                                @ModelAttribute("profileImageForm") ProfileImageForm profileImageForm,
                                Principal principal) {
        LOGGER.info("Accessed /user/account GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClient(user, 0, 4);

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
        final PaginatedSearchResult<JobContact> providersContacted = searchService.getProvidersByClient(user, form.getPage(), 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/profile");
        mav.addObject("results", providersContacted);
        return mav;
    }

    @RequestMapping(path = "/user/follow", method = RequestMethod.POST)
    public ModelAndView follow(@RequestParam("userId") long userId, Principal principal) {
        LOGGER.info("Accessed /user/follow POST controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final User followUser = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getId() != userId) {
            userService.followUser(user, followUser);
        }
        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
        mav.addObject("loggedUser", user);
        return mav;
    }

    @RequestMapping(path = "/user/unfollow", method = RequestMethod.POST)
    public ModelAndView unfollow(@RequestParam("userId") long userId, Principal principal) {
        LOGGER.info("Accessed /user/unfollow POST controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final User followUser = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getId() != userId) {
            userService.unfollowUser(user, followUser);
        }
        final ModelAndView mav = new ModelAndView("redirect:/user/" + userId);
        mav.addObject("loggedUser", user);
        return mav;
    }


    @RequestMapping(path = "/user/{userId}/followers")
    public ModelAndView followers(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
        LOGGER.info("Accessed /user/profile/followers GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user, 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("results", followers);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/followers/search")
    public ModelAndView followersSearch(@PathVariable("userId") long userId,
                                        @ModelAttribute("searchForm") SearchForm form,
                                        BindingResult errors) {
        LOGGER.info("Accessed /user/profile/followers/search GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user));
//        user.setFollowing(userService.getFollowingCount(user));
        final PaginatedSearchResult<User> followers = searchService.getUserFollowers(user, form.getPage(), 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("results", followers);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/following")
    public ModelAndView following(@PathVariable("userId") long userId, @ModelAttribute("searchForm") SearchForm form) {
        LOGGER.info("Accessed /user/profile/following GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user));
//        user.setFollowing(userService.getFollowingCount(user));
        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user, 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/profile/followersPage");
        mav.addObject("flag", true);
        mav.addObject("results", following);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(path = "/user/{userId}/following/search")
    public ModelAndView followingSearch(@PathVariable("userId") long userId,
                                        @ModelAttribute("searchForm") SearchForm form,
                                        BindingResult errors) {
        LOGGER.info("Accessed /user/profile/following/search GET controller");

        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        user.setFollowers(userService.getFollowersCount(user));
//        user.setFollowing(userService.getFollowingCount(user));
        final PaginatedSearchResult<User> following = searchService.getUserFollowing(user, form.getPage(), 4);
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
            boolean followed = loggedUser.getFollowing().stream().anyMatch(user -> user.getId() == userId);
            mav.addObject("loggedUser", loggedUser);
            mav.addObject("followed", followed);
        }
        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
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
    public ModelAndView updateCoverImage( @ModelAttribute("searchForm") SearchForm searchForm,
                                          @ModelAttribute("profileImageForm") ProfileImageForm profileImageForm,
                                          @ModelAttribute("coverImageForm") CoverImageForm coverImageForm,
                                          BindingResult errors,
                                         Principal principal) {
        LOGGER.info("Accessed /user/account/updateCoverImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypesNoGIF().contains(coverImageForm.getCoverImage().getContentType())) {
            LOGGER.warn("Image content type is not valid");
            errors.rejectValue("coverImage","errors.IllegalContentTypeException");
            return profile(searchForm,coverImageForm,profileImageForm,principal);
        }
        try {
            userService.updateCoverImage(new ImageDto(coverImageForm.getCoverImage().getBytes(), coverImageForm.getCoverImage().getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new ServerInternalException();
        }
        return new ModelAndView("redirect:/user/account");
    }


    @RequestMapping(value = "/user/account/updateProfileImage", method = RequestMethod.POST)
    public ModelAndView updateProfileImage( @ModelAttribute("searchForm") SearchForm searchForm, @ModelAttribute("coverImageForm") CoverImageForm coverImageForm, @ModelAttribute("profileImageForm") ProfileImageForm profileImageForm,BindingResult errors,
                                            Principal principal) {
        LOGGER.info("Accessed /user/account/updateProfileImage POST controller");

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        if (!imageService.getContentTypesGIF().contains(profileImageForm.getProfileImage().getContentType())) {
            LOGGER.warn("Image content type is not valid");
            errors.rejectValue("profileImage","errors.IllegalContentTypeExceptionGIF");
            return profile(searchForm,coverImageForm,profileImageForm,principal);
        }
        try {
            userService.updateProfileImage(new ImageDto(profileImageForm.getProfileImage().getBytes(), profileImageForm.getProfileImage().getContentType()), user);
        } catch (IOException e) {
            LOGGER.warn("Error accessing file bytes");
            throw new ServerInternalException();
        }
        return new ModelAndView("redirect:/user/account");
    }

}