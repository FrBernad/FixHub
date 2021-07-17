package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

public abstract class BaseUserDto {

    private long id;
    private String url;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String state;
    private String city;
    private String coverImage;
    private String profileImage;
    private Collection<Roles> roles;
    private Integer followersCount;
    private Integer followingCount;

    public static UriBuilder getUserUriBuilder(User user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getId()));
    }

    public BaseUserDto() {
        // Used by Jersey
    }

    public BaseUserDto(User user, UriInfo uriInfo, SecurityContext securityContext) {
        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);

        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.state = user.getState();
        this.city = user.getCity();
        this.roles = user.getRoles();
        this.url = uriBuilder.clone().build().toString();
        if (user.getProfileImage() != null) {
            this.profileImage = uriBuilder.clone().path("profileImage").build().toString();
        }
        if (user.getCoverImage() != null) {
            this.coverImage = uriBuilder.clone().path("coverImage").build().toString();
        }
        this.followersCount = user.getFollowers().size();
        this.followingCount = user.getFollowing().size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Collection<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }
}
