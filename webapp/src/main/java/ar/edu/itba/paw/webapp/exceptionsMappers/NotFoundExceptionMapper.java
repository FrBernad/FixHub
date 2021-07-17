package ar.edu.itba.paw.webapp.exceptionsMappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);


    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(NotFoundException exception) {
        LOGGER.error("Error encountered, not found exception");

        final String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
