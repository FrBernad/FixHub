package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InvalidImageTypeListValidator implements ConstraintValidator<ImageTypeConstraint, List<MultipartFile>> {


    private ImageTypeConstraint imageTypeConstraint;
    private Collection<String> validTypes;


    @Override
    public void initialize(ImageTypeConstraint imageTypeConstraint) {
        this.imageTypeConstraint = imageTypeConstraint;
        validTypes = Collections.unmodifiableCollection(Arrays.asList(this.imageTypeConstraint.contentType()));
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        for (MultipartFile file: files){
            if(!validTypes.contains(file.getContentType()))
                return false;
        }
        return true;
    }

}

