package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/user/dashboard")
    public ModelAndView dashboard(@ModelAttribute("searchForm") final SearchForm form, @ModelAttribute("searchForm2") final SearchForm form2) {

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProviderId(null, null, user.getId(), 0, 4);

        final UserStats stats = userService.getUserStatsById(user.getId()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProviderId(user.getId(), 0, 1);

        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("results", jobs);
        mav.addObject("contactsResults", contacts);
        mav.addObject("stats", stats);

        return mav;
    }

    @RequestMapping("/user/dashboard/jobs/search")
    public ModelAndView dashboardSearch(@ModelAttribute("searchForm") final SearchForm form) {

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProviderId(form.getQuery(), form.getOrder(), user.getId(), form.getPage(), 4);

        final UserStats stats = userService.getUserStatsById(user.getId()).orElseThrow(UserNotFoundException::new);
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProviderId(user.getId(), 0, 1);

        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("searched", true);
        mav.addObject("results", jobs);
        mav.addObject("stats", stats);
        mav.addObject("contactsResults", contacts);

        return mav;
    }

    @RequestMapping("/user/dashboard/contacts/search")
    public ModelAndView dashboardContactsSearch(@ModelAttribute("searchForm") final SearchForm form) {

        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        final PaginatedSearchResult<JobContact> contacts = searchService.getClientsByProviderId(user.getId(), form.getPage(), 1);

        final UserStats stats = userService.getUserStatsById(user.getId()).orElseThrow(UserNotFoundException::new);
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();

        final PaginatedSearchResult<Job> jobs = searchService.getJobsByProviderId(null, null, user.getId(), 0, 4);

        final ModelAndView mav = new ModelAndView("views/user/dashboard");
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("contactTab", true);
        mav.addObject("contactsResults", contacts);
        mav.addObject("stats", stats);
        mav.addObject("results", jobs);

        return mav;
    }
}
