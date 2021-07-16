package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.ServerInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerInternalExceptionMapper implements ExceptionMapper<ServerInternalException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInternalExceptionMapper.class);

    @Override
    public Response toResponse(ServerInternalException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
}
