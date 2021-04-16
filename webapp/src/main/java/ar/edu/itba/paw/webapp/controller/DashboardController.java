package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Job;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/dashboard")
    public ModelAndView dashboard() {
//        final User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
//        final Collection<Job> jobs = searchService.getJobsByCategory();
        final ModelAndView mav = new ModelAndView("views/dashboard");

        return mav;
    }
}
