package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.LocationService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class SearchController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private LocationService locationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping("/discover")
    public ModelAndView discover(@ModelAttribute("searchForm") final SearchForm form) {
        LOGGER.info("Accessed /discover GET controller");

        final ModelAndView mav = new ModelAndView("views/discover");
        PaginatedSearchResult<Job> results = searchService.getJobsByCategory(null, null, null, null, null, 0, -1);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        Collection<State> stateOptions = locationService.getStates();
        mav.addObject("states", stateOptions);
        mav.addObject("categories", categories);
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("results", results);
        return mav;
    }

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@ModelAttribute("searchForm") final SearchForm form, BindingResult errors) {
        LOGGER.info("Accessed /discover/search GET controller");

        final ModelAndView mav = new ModelAndView("views/discover");
        final String query = form.getQuery(), order = form.getOrder(),
            category = form.getCategory(), state = form.getState(), city = form.getCity();
        final int page = form.getPage();

        PaginatedSearchResult<Job> results = searchService.getJobsByCategory(query, order, category, state, city, page, -1);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        Collection<State> stateOptions = locationService.getStates();

        mav.addObject("states", stateOptions);
        mav.addObject("cities", results.getCities());
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("categories", categories);
        mav.addObject("results", results);

        return mav;
    }

}
