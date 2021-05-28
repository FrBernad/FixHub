package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public class ProfileImageForm {

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
