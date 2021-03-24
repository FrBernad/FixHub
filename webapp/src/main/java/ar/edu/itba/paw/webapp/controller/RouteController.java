package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.JobService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @RequestMapping("/")
    public ModelAndView landingPage() {
        final ModelAndView mav = new ModelAndView("views/landingPage");
        Collection<Job> jobs = jobService.getJobs();
        mav.addObject("jobs", jobs);
        System.out.println(jobs);
        return mav;
    }

    @RequestMapping("/discover")
    public ModelAndView discover() {
        final ModelAndView mav = new ModelAndView("views/discover");
        return mav;
    }

    @RequestMapping(path = {"/create"}, method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam("name") final String name, @RequestParam("password") final String password) {
        User user = userService.createUser(name, password);
        final ModelAndView mav = new ModelAndView("views/landingPage");
        return mav;
    }
}
