package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public class CoverImageForm {

    private MultipartFile coverImage;

    public MultipartFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(MultipartFile coverImage) {
        this.coverImage = coverImage;
    }
}
