package ar.edu.itba.paw.models;

public class ReviewUser {

    private long userId, profileImageId;
    private String name, surname;

    public ReviewUser(long userId, String name, String surname, long profileImageId) {
        this.userId = userId;
        this.profileImageId = profileImageId;
        this.name = name;
        this.surname = surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(long profileImageId) {
        this.profileImageId = profileImageId;
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

}
