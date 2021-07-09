package ar.edu.itba.paw.webapp.dto.customValidations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class InvalidImageSizeListValidator implements ConstraintValidator<ImageSizeConstraint, List<MultipartFile>> {

    private ImageSizeConstraint imageSizeConstraint;
    private int size;

    @Override
    public void initialize(ImageSizeConstraint imageSizeConstraint) {
        this.imageSizeConstraint = imageSizeConstraint;
        size = imageSizeConstraint.size();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (!files.get(0).isEmpty()) {
            for (MultipartFile file : files) {
                if (file.getSize() > size)
                    return false;
            }
        }
        return true;
    }


}
