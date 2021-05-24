package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public class ProfileImageForm {

    private MultipartFile profileImage;

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}
