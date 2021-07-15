package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.IllegalContentTypeExceptionGIF;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalContentTypeGifExceptionMapper  implements ExceptionMapper<IllegalContentTypeExceptionGIF>{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Override
    public Response toResponse(IllegalContentTypeExceptionGIF exception) {
        LOGGER.error("Error encountered, jobNotFoundException caught");
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}

