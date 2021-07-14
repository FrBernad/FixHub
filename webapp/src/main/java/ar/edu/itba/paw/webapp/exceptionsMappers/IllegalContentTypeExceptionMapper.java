package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.IllegalContentTypeException;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider

public class IllegalContentTypeExceptionMapper implements ExceptionMapper<IllegalContentTypeException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Override
    public Response toResponse(IllegalContentTypeException exception) {
        LOGGER.error("Error encountered, IllegalContentTypeException caught");
        return Response.status(Response.Status.BAD_REQUEST).entity("We only support JPEG, JPG, or PNG").build();
    }
}
