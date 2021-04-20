package ar.edu.itba.paw.models;

public class UserInfo {
    private String name, surname, city, state, phoneNumber;
    private ImageDto profileImage;

   public UserInfo(String name, String surname, String city, String state, String phoneNumber, ImageDto profileImage) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }



    public ImageDto getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageDto profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
