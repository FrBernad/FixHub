package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidations.ImageSizeConstraint;
import ar.edu.itba.paw.webapp.form.customValidations.ImageTypeConstraint;
import org.springframework.web.multipart.MultipartFile;

public class CoverImageForm {

    @ImageTypeConstraint(contentType = {"image/png","image/jpeg"})
    @ImageSizeConstraint(size=3000000)
    private MultipartFile coverImage;

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
