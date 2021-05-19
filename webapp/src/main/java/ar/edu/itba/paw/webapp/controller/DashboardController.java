package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Stats;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    @RequestMapping("/user/dashboard")
    public ModelAndView dashboard(@ModelAttribute("searchForm") final SearchForm form,
                                  @ModelAttribute("searchForm") final SearchForm form2,
                                  Principal principal) {

        LOGGER.info("Accessed /user/dashboard GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(null, null, user, 0, 4);

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, 0, 4);

        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("results", jobs);
        mav.addObject("contactsResults", contacts);

        return mav;
    }

    @RequestMapping("/user/dashboard/jobs/search")
    public ModelAndView dashboardSearch(@ModelAttribute("searchForm") final SearchForm form, BindingResult errors, Principal principal) {

        LOGGER.info("Accessed /user/dashboard/jobs/search GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(form.getQuery(), form.getOrder(), user, form.getPage(), 4);

        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("searched", true);
        mav.addObject("results", jobs);
        mav.addObject("contactsResults", contacts);

        return mav;
    }

    @RequestMapping("/user/dashboard/contacts/search")
    public ModelAndView dashboardContactsSearch(@ModelAttribute("searchForm") final SearchForm form, BindingResult errors, Principal principal) {

        LOGGER.info("Accessed /user/dashboard/contacts/search GET controller");

        final User user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProvider(user, form.getPage(), 4);

        //FIXME: ESTO NO VA MAS
//        final Stats stats = userService.getUserStatsById(user.getId()).orElseThrow(UserNotFoundException::new);
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProvider(null, null, user, 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("contactTab", true);
        mav.addObject("contactsResults", contacts);
//       FIXME: ESTO NO VA MAS
//        mav.addObject("stats", stats);
        mav.addObject("results", jobs);

        return mav;
    }
}
