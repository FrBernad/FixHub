package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ImageNotFoundExceptionMapper implements ExceptionMapper<ImageNotFoundException>{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Override
    public Response toResponse(ImageNotFoundException exception) {
        LOGGER.error("Error encountered, ImageNotFoundException caught");
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}

