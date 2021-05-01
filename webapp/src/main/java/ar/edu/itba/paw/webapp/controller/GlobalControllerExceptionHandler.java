package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

    public static final String ERROR_VIEW = "views/errors/errors";

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = JobNotFoundException.class)
    public ModelAndView jobNotFoundException() {
        LOGGER.error("Error encountered, jobNotFoundException caught");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Job", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ImageNotFoundException.class)
    public ModelAndView imageNotFoundException() {
        LOGGER.error("Error encountered, ImageNotFoundException caught");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Image", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ContactInfoNotFoundException.class, UserNotFoundException.class})
    public ModelAndView userNotFoundException() {
        LOGGER.error("Error encountered, ContactInfoNotFoundException caught");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.User", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NoContactFoundException.class)
    public ModelAndView noContactFoundException(){
        LOGGER.error("Error encountered, the user who wants to make the review has not contacted the user ");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NoContactFoundException", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalContentTypeException.class)
    public ModelAndView ilegalContentTypeException() {
        LOGGER.error("Error encountered, IllegalContentTypeException caught");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.IllegalContentTypeException", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MultipartException.class)
    public ModelAndView maxUploadSizeException() {
        LOGGER.error("Error encountered, MultipartException caught (max size exceeded)");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.MaxUploadSizeException", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    /*By default when the DispatcherServlet can't find a handler for a request it sends a 404 response. However if its property "throwExceptionIfNoHandlerFound" is set to true this exception is raised and may be handled with a configured HandlerExceptionResolver.
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/NoHandlerFoundException.html
     * https://stackoverflow.com/questions/13356549/handle-error-404-with-spring-controller/46704230
     * */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView resourceNotFoundException(HttpServletRequest request) {
        LOGGER.error("Error encountered, NoHandlerFoundException (no resource found)");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Resource", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public ModelAndView badRequestException() {
        LOGGER.error("Error encountered, badRequestException");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.BadRequest", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    /*Server error */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {ServerInternalException.class, Exception.class})
    public ModelAndView serverException() {
        LOGGER.error("Error encountered, Exception caught (internal error or specific exception not caught) ");

        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.ServerError", null, locale);
        String code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalContactException.class)
    public ModelAndView illegalContactException(){
        LOGGER.error("Error encountered, the user can't contact himself");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.illegalContactException", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }


}
