package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class InvalidImageTypeValidator implements ConstraintValidator<ImageTypeConstraint, MultipartFile> {


    private ImageTypeConstraint imageTypeConstraint;
    private Collection<String> validTypes;


    @Override
    public void initialize(ImageTypeConstraint imageTypeConstraint) {
        this.imageTypeConstraint = imageTypeConstraint;
        validTypes = Collections.unmodifiableCollection(Arrays.asList(this.imageTypeConstraint.contentType()));
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return validTypes.contains(file.getContentType());
    }

}

