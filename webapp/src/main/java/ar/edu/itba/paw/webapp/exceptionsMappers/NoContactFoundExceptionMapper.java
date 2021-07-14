package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.NoContactFoundException;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoContactFoundExceptionMapper implements ExceptionMapper<NoContactFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);


    public NoContactFoundExceptionMapper() {
    }

    @Override
    public Response toResponse(NoContactFoundException exception) {
        LOGGER.error("Error encountered, there is no contact between the users");
        return Response.status(Response.Status.BAD_REQUEST).entity("Something bad happened. Please try again !!").type("text/plain").build();
    }

}
