package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider

public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Override
    public Response toResponse(ForbiddenException exception) {
        LOGGER.error("Error encountered, ForbiddenException caught by a forbidden action");
        return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
    }
}
