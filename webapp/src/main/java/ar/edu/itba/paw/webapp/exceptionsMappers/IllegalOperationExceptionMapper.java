package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;
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
public class IllegalOperationExceptionMapper implements ExceptionMapper<IllegalOperationException> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(IllegalOperationException exception) {
        final String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());

        return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(message).build();

    }
}
