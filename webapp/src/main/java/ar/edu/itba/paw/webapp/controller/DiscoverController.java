package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.OrderOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView discover() {
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
    public ModelAndView discoverSearch(@RequestParam(value = "searchPhrase", required = false) final String phrase,
                                       @RequestParam(value = "filter", required = false) final String filter,
                                       @RequestParam(value = "order", required = false) final String order
    ) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = searchService.getJobs(phrase, order, filter);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        Collection<OrderOptions> orderOptions = Arrays.asList(OrderOptions.values().clone());
        mav.addObject("orderOptions", orderOptions);
        mav.addObject("jobs", jobs);
        mav.addObject("searchPhrase", phrase);
        mav.addObject("order", order);
        mav.addObject("filter", filter);
        mav.addObject("filters", categories);
        return mav;
    }

}
