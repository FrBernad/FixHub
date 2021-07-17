package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.NotificationNotFoundException;
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
public class NotificationNotFoundMapper implements ExceptionMapper<NotificationNotFoundException> {

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationNotFoundMapper.class);

    @Override
    public Response toResponse(NotificationNotFoundException exception) {
        LOGGER.error("Error encountered, notification not found exception");

        final String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
