package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private JobService jobService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

//    @RequestMapping("/user/dashboard")
//    public ModelAndView dashboard(@ModelAttribute("searchForm") final SearchDto form,
//                                  @ModelAttribute("searchForm") final SearchDto form2,
//                                  Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard GET controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(null, null, user, 0, 4);
//
//        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, 0, 4);
//
//        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
//        final ModelAndView mav = new ModelAndView("views/user/dashboard");
//        mav.addObject("loggedUser", user);
//        mav.addObject("orderOptions", orderOptions);
//        mav.addObject("results", jobs);
//        mav.addObject("contactsResults", contacts);
//
//        return mav;
//    }
//
//    @RequestMapping("/user/dashboard/jobs/search")
//    public ModelAndView dashboardSearch(@ModelAttribute("searchForm") final SearchDto form, BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard/jobs/search GET controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(form.getQuery(), form.getOrder(), user, form.getPage(), 4);
//
//        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
//
//        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, 0, 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/dashboard");
//        mav.addObject("loggedUser", user);
//        mav.addObject("orderOptions", orderOptions);
//        mav.addObject("searched", true);
//        mav.addObject("results", jobs);
//        mav.addObject("contactsResults", contacts);
//
//        return mav;
//    }
//
//    @RequestMapping("/user/dashboard/contacts/search")
//    public ModelAndView dashboardContactsSearch(@ModelAttribute("searchForm") final SearchDto form, BindingResult errors, Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard/contacts/search GET controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, form.getPage(), 4);
//
//        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
//
//        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(null, null, user, 0, 4);
//
//        final ModelAndView mav = new ModelAndView("views/user/dashboard");
//        mav.addObject("loggedUser", user);
//        mav.addObject("orderOptions", orderOptions);
//        mav.addObject("contactTab", true);
//        mav.addObject("contactsResults", contacts);
//        mav.addObject("results", jobs);
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/user/dashboard/contacts/acceptJob", method = RequestMethod.POST)
//    public ModelAndView acceptJob(@RequestParam("contactId") final long contactId, Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard/contacts/acceptJob POST controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
//
//        if (!user.getId().equals(jobContact.getProvider().getId())) {
//            LOGGER.debug("Provider tried to change a job status it does not own");
//            throw new NoContactFoundException();
//        }
//
//        jobService.acceptJob(jobContact);
//
//        return new ModelAndView("redirect:/user/dashboard");
//
//    }
//
//    @RequestMapping(value = "/user/dashboard/contacts/rejectJob", method = RequestMethod.POST)
//    public ModelAndView rejectJob(@RequestParam("contactId") final long contactId, Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard/contacts/rejectJob POST controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
//
//        if (!user.getId().equals(jobContact.getProvider().getId())) {
//            LOGGER.debug("Provider tried to change a job status it does not own");
//            throw new NoContactFoundException();
//        }
//
//        jobService.rejectJob(jobContact);
//
//        return new ModelAndView("redirect:/user/dashboard");
//
//    }
//
//    @RequestMapping(value = "/user/dashboard/contacts/completedJob", method = RequestMethod.POST)
//    public ModelAndView completedJob(@RequestParam("contactId") final long contactId, Principal principal) {
//
//        LOGGER.info("Accessed /user/dashboard/contacts/completedJob POST controller");
//
//        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//
//        final JobContact jobContact = jobService.getContactById(contactId).orElseThrow(NoContactFoundException::new);
//
//        if (!user.getId().equals(jobContact.getProvider().getId())) {
//            LOGGER.debug("Provider tried to change a job status it does not own");
//            throw new NoContactFoundException();
//        }
//
//        jobService.finishJob(jobContact);
//
//        return new ModelAndView("redirect:/user/dashboard");
//
//    }


}
