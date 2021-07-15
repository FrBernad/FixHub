package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.IllegalOperationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalOperationExceptionMapper implements ExceptionMapper<IllegalOperationException> {

    @Override
    public Response toResponse(IllegalOperationException exception) {
        return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();

    }
}
