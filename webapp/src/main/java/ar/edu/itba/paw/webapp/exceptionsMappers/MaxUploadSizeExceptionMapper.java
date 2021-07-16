package ar.edu.itba.paw.webapp.exceptionsMappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MaxUploadSizeExceptionMapper implements ExceptionMapper<MaxUploadSizeExceededException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaxUploadSizeExceptionMapper.class);

    @Override
    public Response toResponse(MaxUploadSizeExceededException exception) {
        LOGGER.error("Error encountered, MultipartException caught (max size exceeded)");
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}

