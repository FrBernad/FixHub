package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobService;
import ar.edu.itba.paw.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class DiscoverController {

    @Autowired
    private JobService jobService;


    @RequestMapping("/discover")
    public ModelAndView discover(@RequestParam(value = "filterBy", defaultValue = "0") final long filterBy, @RequestParam(value = "orderBy", defaultValue = "1") final long orderBy) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobs();
        mav.addObject("jobs", jobs);
        mav.addObject("order", orderBy);
        mav.addObject("filter", filterBy);
        return mav;
    }

    //     @RequestMapping("/discover/filter")
//    public ModelAndView discoverFilter(@RequestParam(value = "filterBy",defaultValue = "0") final long filterBy) {
//        final ModelAndView mav = new ModelAndView("views/discover");
//        Collection<Job> jobs = jobService.getJobs();
//        mav.addObject("jobs", jobs);
//        mav.addObject("filter", filterBy);
//        System.out.println(jobs);
//        return mav;
//    }

    @RequestMapping("/discover/search")
    public ModelAndView discoverSearch(@RequestParam("searchPhrase") final String phrase) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobsBySearchPhrase(phrase);
        mav.addObject("jobs", jobs);
        mav.addObject("searchPhrase", phrase);
        return mav;
    }

    @RequestMapping("/discover/searchCategory")
    public ModelAndView discoverSearchCategory(@RequestParam("category") final String phrase) {
        final ModelAndView mav = new ModelAndView("views/discover");
        Collection<Job> jobs = jobService.getJobsBySearchCategory(phrase);
        mav.addObject("jobs", jobs);
        return mav;
    }
}
