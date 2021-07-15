package ar.edu.itba.paw.webapp.exceptionsMappers;

import ar.edu.itba.paw.webapp.dto.response.BeanValidationErrorDto;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Singleton
@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        ArrayList<BeanValidationErrorDto> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            errors.add(
                new BeanValidationErrorDto(
                    violation.getMessage()
                )
            );
        }

        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new GenericEntity<Collection<BeanValidationErrorDto>>(errors) {
            })
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

}