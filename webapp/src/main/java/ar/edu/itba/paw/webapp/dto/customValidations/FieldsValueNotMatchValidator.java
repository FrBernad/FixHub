package ar.edu.itba.paw.webapp.dto.customValidations;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueNotMatchValidator  implements ConstraintValidator<FieldsValueNotMatch, Object> {

    private String field;
    private String fieldNotMatch;

    public void initialize(FieldsValueNotMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldNotMatch = constraintAnnotation.fieldNotMatch();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldNotMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldNotMatch);

        if (fieldValue != null)
            return !fieldValue.equals(fieldNotMatchValue);
        else
            return fieldNotMatchValue == null;
    }
}