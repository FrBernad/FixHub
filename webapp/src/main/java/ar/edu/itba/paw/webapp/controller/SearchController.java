package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.SearchResult;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;

@Controller
public class SearchController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/discover")
    public ModelAndView discover(@ModelAttribute("searchForm") final SearchForm form) {
        final ModelAndView mav = new ModelAndView("views/discover");
        SearchResult results = searchService.getJobsByCategory(null, null, null);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        mav.addObject("filters", categories);
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("jobs", results.getJobs());
        return mav;
    }

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@ModelAttribute("searchForm") final SearchForm form, BindingResult errors) {
        final ModelAndView mav = new ModelAndView("views/discover");
        final String query = form.getQuery(), order = form.getOrder(), filter = form.getFilter();

        SearchResult results = searchService.getJobsByCategory(query, order, filter);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = Arrays.asList(OrderOptions.values().clone());

        mav.addObject("orderOptions", orderOptions);
        mav.addObject("filters", categories);
        mav.addObject("jobs", results.getJobs());

        mav.addObject("searchPhrase", results.getQuery());
        mav.addObject("order", results.getOrder());
        mav.addObject("filter", results.getFilter());

        return mav;
    }

}
