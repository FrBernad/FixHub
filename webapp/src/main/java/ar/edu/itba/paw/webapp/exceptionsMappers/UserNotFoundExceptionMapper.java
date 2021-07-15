package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(UserNotFoundException exception) {
        LOGGER.error("Error encountered, user not found exception");
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}
