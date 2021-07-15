package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DuplicateUserExceptionMapper implements ExceptionMapper<DuplicateUserException> {

    @Override
    public Response toResponse(DuplicateUserException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}
