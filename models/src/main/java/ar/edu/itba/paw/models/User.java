package ar.edu.itba.paw.models;

import java.util.Collection;
import java.util.Objects;

public class User {
    private String name, password, surname, city, state, phoneNumber, email;
    private Long id, profileImageId, coverImageId;
    private Collection<Roles> roles;
    private int followers;
    private int following;

    public User(Long id, String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles, Long profileImageId, Long coverImageId) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.city = city;
        this.roles = roles;
        this.coverImageId = coverImageId;
        this.profileImageId = profileImageId;
    }

    public Long getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Long profileImageId) {
        this.profileImageId = profileImageId;
    }

    public Long getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(Long coverImageId) {
        this.coverImageId = coverImageId;
    }

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(p -> p.name().equals(role));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles role) {
        roles.add(role);
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean getIsProvider(){
        return roles.stream().anyMatch(p -> p.name().equals(Roles.PROVIDER.name()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password) && surname.equals(user.surname) && city.equals(user.city) && state.equals(user.state) && phoneNumber.equals(user.phoneNumber) && email.equals(user.email) && id.equals(user.id) && Objects.equals(profileImageId, user.profileImageId) && Objects.equals(coverImageId, user.coverImageId) && roles.containsAll(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id);
    }
}
