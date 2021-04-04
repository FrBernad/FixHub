package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;

@Controller
public class DiscoverController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/discover")
    public ModelAndView discover(@ModelAttribute("searchForm") final SearchForm form) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = searchService.getJobs(null, null, null);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = searchService.getOrderOptions();
        mav.addObject("filters", categories);
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("jobs", jobs);
        return mav;
    }

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@ModelAttribute("searchForm") final SearchForm form, BindingResult errors) {
        final ModelAndView mav = new ModelAndView("views/discover");
        final String query = form.getQuery(), order = form.getOrder(), filter = form.getFilter();

        Collection<Job> jobs = searchService.getJobs(query, order, filter);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = Arrays.asList(OrderOptions.values().clone());

        mav.addObject("orderOptions", orderOptions);
        mav.addObject("filters", categories);
        mav.addObject("jobs", jobs);

        mav.addObject("searchPhrase", query);
        mav.addObject("order", order);
        mav.addObject("filter", filter);
        return mav;
    }

}
