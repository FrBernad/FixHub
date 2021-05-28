package ar.edu.itba.paw.webapp.form;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {InvalidImageSizeValidator.class,InvalidImageSizeListValidator.class})
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageSizeConstraint {
    String message() default "Image size is too big";
    int size();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}