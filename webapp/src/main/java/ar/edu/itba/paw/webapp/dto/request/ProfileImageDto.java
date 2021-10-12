package ar.edu.itba.paw.webapp.dto.request;

import ar.edu.itba.paw.webapp.dto.customValidations.ImageSizeConstraint;
import ar.edu.itba.paw.webapp.dto.customValidations.ImageTypeConstraint;
import org.springframework.web.multipart.MultipartFile;

public class ProfileImageDto {

    @ImageTypeConstraint(contentType = {"image/png","image/jpeg","image/gif"})
    @ImageSizeConstraint(size=3000000)
    private MultipartFile profileImage;


    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}
