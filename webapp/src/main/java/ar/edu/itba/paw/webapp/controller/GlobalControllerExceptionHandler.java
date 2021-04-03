package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.ReviewException;
import ar.edu.itba.paw.webapp.exceptions.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public static final String NOT_FOUND_VIEW = "views/pageNotFound";


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = JobNotFoundException.class)
    public ModelAndView jobNotFoundException() {
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        return mav;
    }

    /*TODO: VER COMO HACER ESTO*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ReviewException.class)
    public ModelAndView jobNotFoundReviewException() {
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        return mav;
    }

}
