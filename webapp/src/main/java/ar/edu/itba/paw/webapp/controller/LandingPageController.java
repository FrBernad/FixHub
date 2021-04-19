package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import ar.edu.itba.paw.models.PaginatedSearchResult;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class LandingPageController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/")
    public ModelAndView landingPage(@ModelAttribute("searchForm") final SearchForm form) {
        final ModelAndView mav = new ModelAndView("views/landingPage");
        PaginatedSearchResult<Job> results = searchService.getJobsByCategory(null, "MOST_POPULAR", null, 0, -1);
        Collection<JobCategory> categories = jobService.getJobsCategories();
        mav.addObject("jobs", results.getResults());
        mav.addObject("categories", categories);
        return mav;
    }

}
