package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider

public class IllegalContactExceptionMapper implements ExceptionMapper<IllegalContactException> {

    @Override
    public Response toResponse(IllegalContactException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

