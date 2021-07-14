package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.JobNotFoundException;
import ar.edu.itba.paw.webapp.controller.GlobalControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JobNotFoundExceptionMapper implements ExceptionMapper<JobNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    public JobNotFoundExceptionMapper() {
    }

    @Override
    public Response toResponse(JobNotFoundException ex) {
        LOGGER.error("Error encountered, IllegalContentTypeException caught because user tried ");
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
    }
}
