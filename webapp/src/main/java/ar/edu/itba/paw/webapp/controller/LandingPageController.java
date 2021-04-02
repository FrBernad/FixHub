package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.JobCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class LandingPageController {

    @Autowired
    private JobService jobService;

    @RequestMapping("/")
    public ModelAndView landingPage() {
        final ModelAndView mav = new ModelAndView("views/landingPage");
        Collection<Job> jobs = jobService.getJobs();
        Collection<JobCategory> categories = jobService.getJobsCategories();
        mav.addObject("jobs", jobs);
        mav.addObject("categories", categories);
        return mav;
    }

}
