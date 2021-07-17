package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.MaxUploadSizeRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MaxUploadSizeExceptionMapper implements ExceptionMapper<MaxUploadSizeRequestException> {

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(MaxUploadSizeExceptionMapper.class);

    @Override
    public Response toResponse(MaxUploadSizeRequestException exception) {
        final String message = messageSource.getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());

        LOGGER.error("Error encountered, MaxUploadRequestException caught (max size exceeded)");
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}

