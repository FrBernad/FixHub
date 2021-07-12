package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.pagination.PaginatedSearchResult;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserInfo;
import ar.edu.itba.paw.webapp.auth.JwtUtil;
import ar.edu.itba.paw.webapp.dto.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

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
    private LocationService locationService;

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response loginUser(UserAuthDto userAuthDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDto.getEmail(), userAuthDto.getPassword())
            );

            final User user = userService.getUserByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

            final Response.ResponseBuilder responseBuilder = Response.noContent();

            addAuthorizationHeader(responseBuilder, user);

            return responseBuilder.build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUser(@Valid UserInfoDto userInfoDto) {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        userService.updateUserInfo(
            new UserInfo(userInfoDto.getName(), userInfoDto.getSurname(),
                userInfoDto.getCity(), userInfoDto.getState(), userInfoDto.getPhoneNumber()),
            user);

        return Response.ok().build();
    }


    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUser() {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if (user.getRoles().contains(Roles.PROVIDER)) {
            return Response.ok(new ProviderDto(user, uriInfo)).build();
        }
        return Response.ok(new UserDto(user, uriInfo)).build();

    }

    @GET
    @Path("coverImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getUserCoverImage(@Context Request request) {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final byte[] coverImage = user.getCoverImage().getData();

        return Response.ok(coverImage).type(user.getCoverImage().getMimeType()).build();
    }

    @PUT
    @Path("coverImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserCoverImage() {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        return Response.ok().build();
    }

    @GET
    @Path("profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON})
    public Response getUserProfileImage() {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final byte[] profileImage = user.getProfileImage().getData();

        return Response.ok(profileImage).type(user.getProfileImage().getMimeType()).build();
    }

    @PUT
    @Path("profileImage")
    public Response updateUserProfileImage() {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        return Response.ok().build();
    }

    @GET
    @Path("/jobs/requests")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserJobRequests(
        @QueryParam("status") String status,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("6") int pageSize
    ) {
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> results = searchService.getClientsByProvider(user, status, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<JobContactDto> contactsDto = JobContactDto.mapContactToDto(results.getResults(), uriInfo);

        final PaginatedResultDto<JobContactDto> resultsDto =
            new PaginatedResultDto<>(
                results.getPage(),
                results.getTotalPages(),
                contactsDto);

        final UriBuilder uriBuilder = uriInfo
            .getAbsolutePathBuilder()
            .queryParam("pageSize", pageSize);

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<JobContactDto>>(resultsDto) {
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
        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> results = searchService.getJobsByProvider(query, order, user, page, pageSize);

        if (results == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Collection<JobDto> jobsDto = JobDto.mapJobToDto(results.getResults(), uriInfo);

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

        return createPaginationResponse(results, new GenericEntity<PaginatedResultDto<JobDto>>(resultsDto) {
        }, uriBuilder);
    }


//    @RequestMapping(path = "/register", method = RequestMethod.POST)
//    public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, final HttpServletRequest request) {
//        LOGGER.info("Accessed /register POST controller");
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form RegisterForm data");
//            return register(form);
//        }
//
//        User user;
//        final ModelAndView mav = new ModelAndView("redirect:/user/account");
//        try {
//            user = userService.createUser(form.getPassword(),
//                form.getName(), form.getSurname(),
//                form.getEmail(), form.getPhoneNumber(),
//                form.getState(), form.getCity());
//
//            forceLogin(user, request);
//            mav.addObject("loggedUser", user);
//        } catch (DuplicateUserException e) {
//            LOGGER.warn("Error in form RegisterForm, email is already in used");
//            errors.rejectValue("email", "validation.user.DuplicateEmail");
//            return register(form);
//        }
//
//        return mav;
//    }
//
//    @RequestMapping("/login")
//    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false") boolean error) {
//        LOGGER.info("Accessed /login GET controller with error value {}", error);
//
//        final ModelAndView mav = new ModelAndView("views/login");
//        mav.addObject("error", error);
//        return mav;
//    }
//
//    //VERIFY ACCOUNT
//
//    @RequestMapping(path = "/verify")
//    public ModelAndView verifyAccount(HttpServletRequest request,
//                                      @RequestParam(defaultValue = "") String token) {
//        LOGGER.info("Accessed /user/verifyAccount GET controller");
//
//        final Optional<User> userOptional = userService.verifyAccount(token);
//        boolean success = false;
//        final ModelAndView mav = new ModelAndView("views/user/account/verification/verify");
//        if (userOptional.isPresent()) {
//            success = true;
//            User user = userOptional.get();
//            LOGGER.debug("Updating user {} credentials", user.getId());
//            forceLogin(user, request);
//            mav.addObject("loggedUser", user);
//        }
//        mav.addObject("success", success);
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/verifyAccount/resend", method = RequestMethod.POST)
//    public ModelAndView resendAccountVerification(Principal principal) {
//        LOGGER.info("Accessed /user/verifyAccount/resend GET controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        userService.resendVerificationToken(user);
//
//        final ModelAndView mav = new ModelAndView("redirect:/user/verifyAccount/resendConfirmation");
//        mav.addObject("loggedUser", user);
//
//        return mav;
//    }
//
//    @RequestMapping("/user/verifyAccount/resendConfirmation")
//    public ModelAndView verificationResendConfirmation() {
//        LOGGER.info("Accessed /user/verifyAccount/resendConfirmation GET controller");
//        return new ModelAndView("views/user/account/verification/resendConfirmation");
//    }
//
//    //RESET PASSWORD
//
//    @RequestMapping(path = "/user/resetPasswordRequest")
//    public ModelAndView resetPasswordRequest(@ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form) {
//        LOGGER.info("Accessed /user/resetPasswordRequest GET controller");
//
//        return new ModelAndView("views/user/account/password/resetRequest");
//    }
//
//    @RequestMapping(path = "/user/resetPasswordRequest", method = RequestMethod.POST)
//    public ModelAndView sendPasswordReset(@Valid @ModelAttribute("resetPasswordEmailForm") final ResetPasswordEmailForm form,
//                                          BindingResult errors) {
//        LOGGER.info("Accessed /user/resetPasswordRequest POST controller");
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form ResetPasswordForm");
//            return resetPasswordRequest(form);
//        }
//
//        final Optional<User> user = userService.getUserByEmail(form.getEmail());
//        if (!user.isPresent()) {
//            LOGGER.warn("Error in form ResetPasswordForm, user under email {} not found", form.getEmail());
//            errors.rejectValue("email", "errors.invalidEmail");
//            return resetPasswordRequest(form);
//        }
//
//        userService.generateNewPassword(user.get());
//
//        return new ModelAndView("views/user/account/password/resetEmailConfirmation");
//    }
//
//    @RequestMapping(path = "/user/resetPassword")
//    public ModelAndView resetPassword(@RequestParam(defaultValue = "") String token,
//                                      @ModelAttribute("resetPasswordForm") final ResetPasswordForm form) {
//        LOGGER.info("Accessed /user/resetPassword GET controller");
//
//        if (userService.validatePasswordReset(token)) {
//            final ModelAndView mav = new ModelAndView("views/user/account/password/reset");
//            mav.addObject("token", token);
//            return mav;
//        }
//
//        LOGGER.debug("Token reset password is invalid");
//        return new ModelAndView("redirect:/");
//    }
//
//    @RequestMapping(path = "/user/resetPassword", method = RequestMethod.POST)
//    public ModelAndView resetPassword(HttpServletRequest request,
//                                      @Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm form,
//                                      BindingResult errors) {
//
//        LOGGER.info("Accessed /user/resetPassword POST controller");
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form resetPasswordForm data");
//            return new ModelAndView("views/user/account/password/reset");
//        }
//
//        final Optional<User> userOptional = userService.updatePassword(form.getToken(), form.getPassword());
//        boolean success = false;
//
//        final ModelAndView mav = new ModelAndView("views/user/account/password/resetResult");
//        if (userOptional.isPresent()) {
//            success = true;
//            User user = userOptional.get();
//            LOGGER.debug("Updated user credentials");
//            forceLogin(user, request);
//            mav.addObject("loggedUser", user);
//        }
//        mav.addObject("success", success);
//        return mav;
//    }
//
//    //JOIN STEPS
//    @RequestMapping("/user/join")
//    public ModelAndView join(@ModelAttribute("joinForm") final FirstJoinForm form, Principal principal) {
//        LOGGER.info("Accessed /user/join GET controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if (user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is already provider", user.getId());
//            return new ModelAndView("redirect:/user/dashboard");
//        }
//        ModelAndView mav = new ModelAndView("views/user/account/roles/join");
//        mav.addObject("states", locationService.getStates());
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/join", method = RequestMethod.POST)
//    public ModelAndView joinPost(@Valid @ModelAttribute("joinForm") final FirstJoinForm form,
//                                 final BindingResult errors,
//                                 RedirectAttributes ra,
//                                 Principal principal) {
//        LOGGER.info("Accessed /user/join POST controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if (user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is already provider", user.getId());
//            return new ModelAndView("redirect:/user/dashboard");
//        }
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form FirstJoinForm data");
//            return join(form, principal);
//        }
//
//        ra.addFlashAttribute("status", form.getState());
//        ra.addFlashAttribute("startTime", form.getStartTime());
//        ra.addFlashAttribute("endTime", form.getEndTime());
//        final State status = locationService.getStateById(form.getState()).orElseThrow(StateNotFoundException::new);
//        ra.addFlashAttribute("cities", locationService.getCitiesByState(state));
//
//        LOGGER.debug("Added redirect attributes to /user/join/chooseCity");
//
//        return new ModelAndView("redirect:/user/join/chooseCity");
//    }
//
//    @RequestMapping("/user/join/chooseCity")
//    public ModelAndView joinChooseCity(@ModelAttribute("chooseCityForm") final SecondJoinForm form,
//                                       HttpServletRequest request, Principal principal) {
//        LOGGER.info("Accessed /user/join/chooseCity GET controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if (user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is already provider", user.getId());
//            return new ModelAndView("redirect:/user/dashboard");
//        }
//
//        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//        if (flashMap == null && form.getState() == 0) {
//            LOGGER.warn("Flashmap and form are null redirecting to first step");
//            return new ModelAndView("redirect:/user/join");
//        }
//
//        ModelAndView mav = new ModelAndView("views/user/account/roles/chooseCity");
//
//        if (flashMap != null)
//            mav.addAllObjects(flashMap);
//
//        if (form != null && form.getState() != 0) {
//            LOGGER.debug("Adding state cities again due to error in chooseCity form");
//            final State state = locationService.getStateById(form.getState()).orElseThrow(StateNotFoundException::new);
//            mav.addObject("cities", locationService.getCitiesByState(state));
//        }
//
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/user/join/chooseCity", method = RequestMethod.POST)
//    public ModelAndView joinChooseCityPost(@Valid @ModelAttribute("chooseCityForm") final SecondJoinForm form,
//                                           final BindingResult errors,
//                                           HttpServletRequest request,
//                                           Principal principal) {
//        LOGGER.info("Accessed /user/join/chooseCity POST controller");
//
//        User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//        if (user.hasRole(Roles.PROVIDER)) {
//            LOGGER.warn("User {} is already provider", user.getId());
//            return new ModelAndView("redirect:/user/dashboard");
//        }
//
//        if (errors.hasErrors()) {
//            LOGGER.warn("Error in form SecondJoinForm data");
//            return joinChooseCity(form, request, principal);
//        }
//
//        userService.makeProvider(user, form.getCity(), form.getStartTime(), form.getEndTime());
//
//        user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        LOGGER.debug("Updating user {} credentials", user.getId());
//        forceLogin(user, request);
//
//        return new ModelAndView("redirect:/user/dashboard");
//    }
//
//    private void forceLogin(User user, HttpServletRequest request) {
//        LOGGER.debug("forcing login of user {}", user.getId());
//
//        //generate authentication
//        final PreAuthenticatedAuthenticationToken token =
//            new PreAuthenticatedAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
//
//        token.setDetails(new WebAuthenticationDetails(request));
//
//        final SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(token);
//
//        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
//        return roles.
//            stream()
//            .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//            .collect(Collectors.toList());
//    }


    private void addAuthorizationHeader(Response.ResponseBuilder responseBuilder, User user) {
        responseBuilder.header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user));
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
