package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.JobNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public static final String NOT_FOUND_VIEW = "views/pageNotFound";
    public static final String ERROR_VIEW = "views/serverError";
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = JobNotFoundException.class)
    public ModelAndView jobNotFoundException() {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.job", null, locale);
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        mav.addObject("errors",error);
        return mav;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ModelAndView userNotFoundException() {
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        return mav;
    }


    /*By default when the DispatcherServlet can't find a handler for a request it sends a 404 response. However if its property "throwExceptionIfNoHandlerFound" is set to true this exception is raised and may be handled with a configured HandlerExceptionResolver.
    * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/NoHandlerFoundException.html
    * https://stackoverflow.com/questions/13356549/handle-error-404-with-spring-controller/46704230
    * */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView resourceNotFoundException(){
        ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public ModelAndView badRequestException(){
        ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        return mav;
    }

    /*Server error */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView serverException(){
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        return mav;
    }


}
