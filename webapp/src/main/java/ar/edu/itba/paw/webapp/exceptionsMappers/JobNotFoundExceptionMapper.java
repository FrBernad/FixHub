package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.JobNotFoundException;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class JobNotFoundExceptionMapper implements ExceptionMapper<JobNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    public JobNotFoundExceptionMapper() {
    }

    @Override
    public Response toResponse(JobNotFoundException ex) {

        final String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());

        LOGGER.error("Error encountered, IllegalContentTypeException caught because user tried ");
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
