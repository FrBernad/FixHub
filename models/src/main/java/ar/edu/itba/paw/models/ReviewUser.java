package ar.edu.itba.paw.models;

import java.util.Objects;

public class ReviewUser {

    private long userId;
    private Long profileImageId;
    private String name, surname;

    public ReviewUser(long userId, String name, String surname, Long profileImageId) {
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

    public Long getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Long profileImageId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewUser that = (ReviewUser) o;
        return userId == that.userId && profileImageId == that.profileImageId && Objects.equals(name, that.name) && Objects.equals(surname, that.surname);
    }

}
