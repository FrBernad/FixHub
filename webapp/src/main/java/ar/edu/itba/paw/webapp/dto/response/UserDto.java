package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDto {

    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String state;
    private String city;

    private Integer followersCount;
    private Integer followingCount;

    private String url;

    private String followersUrl;
    private String followingUrl;

    private String contactInfoUrl;

    private String receivedRequestsUrl;
    private String sentRequestsUrl;

    private String coverImageUrl;
    private String profileImageUrl;

    private String jobsUrl;

    private String providerDetailsUrl;

    private String notificationsUrl;

    public static Collection<UserDto> mapUserToDto(Collection<User> users, UriInfo uriInfo) {
        return users.stream().map(u -> new UserDto(u, uriInfo)).collect(Collectors.toList());
    }

    public static UriBuilder getUserUriBuilder(User user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone().path("users").path(String.valueOf(user.getId()));
    }

    public UserDto() {
        // Used by Jersey
    }

    public UserDto(User user, UriInfo uriInfo) {
        final UriBuilder uriBuilder = getUserUriBuilder(user, uriInfo);

        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.state = user.getState();
        this.city = user.getCity();
        this.url = uriBuilder.clone().build().toString();
        if (user.getProfileImage() != null) {
            this.profileImageUrl = uriBuilder.clone().path("profileImage").build().toString();
        } else {
            this.profileImageUrl = "";
        }
        if (user.getCoverImage() != null) {
            this.coverImageUrl = uriBuilder.clone().path("coverImage").build().toString();
        } else {
            this.coverImageUrl = "";
        }
        this.followersCount = user.getFollowers().size();
        this.followingCount = user.getFollowing().size();

        this.followersUrl = uriBuilder.clone().path("followers").build().toString();
        this.followingUrl = uriBuilder.clone().path("following").build().toString();

        this.sentRequestsUrl = uriBuilder.clone().path("requests").path("sent").build().toString();
        this.receivedRequestsUrl = uriBuilder.clone().path("requests").path("received").build().toString();

        this.contactInfoUrl = uriBuilder.clone().path("contactInfo").build().toString();

        this.jobsUrl = uriBuilder.clone().path("jobs").build().toString();

        this.providerDetailsUrl = user.isProvider() ? uriBuilder.clone().path("provider").build().toString() : "";

        this.notificationsUrl = uriBuilder.clone().path("notifications").build().toString();

    }

    public String getNotificationsUrl() {
        return notificationsUrl;
    }

    public void setNotificationsUrl(String notificationsUrl) {
        this.notificationsUrl = notificationsUrl;
    }

    public String getProviderDetailsUrl() {
        return providerDetailsUrl;
    }

    public void setProviderDetailsUrl(String providerDetailsUrl) {
        this.providerDetailsUrl = providerDetailsUrl;
    }

    public String getJobsUrl() {
        return jobsUrl;
    }

    public void setJobsUrl(String jobsUrl) {
        this.jobsUrl = jobsUrl;
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

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public String getContactInfoUrl() {
        return contactInfoUrl;
    }

    public void setContactInfoUrl(String contactInfoUrl) {
        this.contactInfoUrl = contactInfoUrl;
    }

    public String getReceivedRequestsUrl() {
        return receivedRequestsUrl;
    }

    public void setReceivedRequestsUrl(String receivedRequestsUrl) {
        this.receivedRequestsUrl = receivedRequestsUrl;
    }

    public String getSentRequestsUrl() {
        return sentRequestsUrl;
    }

    public void setSentRequestsUrl(String sentRequestsUrl) {
        this.sentRequestsUrl = sentRequestsUrl;
    }
}
