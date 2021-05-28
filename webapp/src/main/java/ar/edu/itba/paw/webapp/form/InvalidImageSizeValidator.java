package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InvalidImageSizeValidator implements ConstraintValidator<ImageSizeConstraint, MultipartFile> {

    private ImageSizeConstraint imageSizeConstraint;
    private int size;

    @Override
    public void initialize(ImageSizeConstraint imageSizeConstraint) {
        this.imageSizeConstraint = imageSizeConstraint;
        size = imageSizeConstraint.size();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file.getSize() <= size;
    }
}


