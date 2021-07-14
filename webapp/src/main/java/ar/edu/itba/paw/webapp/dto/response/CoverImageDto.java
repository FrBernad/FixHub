package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.webapp.dto.customValidations.ImageSizeConstraint;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import org.springframework.web.multipart.MultipartFile;

public class CoverImageDto {

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
