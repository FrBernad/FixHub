package ar.edu.itba.paw.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class RootDto {

    private String usersUrl;
    private String notificationsUrl;
    private String sentRequestsUrl;
    private String receivedRequestsUrl;
    private String userJobsUrl;
    private String followersUrl;
    private String followingUrl;
    private String jobsUrl;
    private String statesUrl;
    private String citiesUrl;

    public RootDto() {
    }

    public RootDto(UriInfo uriInfo) {
        usersUrl = uriInfo.getBaseUriBuilder().path("users").path("{id}").build().toString();
        notificationsUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("notifications")
            .path("{{?onlyNew,page,pageSize}").build().toString();
        sentRequestsUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("requests").path("sent")
            .path("{{?status,page,order,pageSize}").build().toString();
        receivedRequestsUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("requests").path("received")
            .path("{?status,page,order,pageSize}").build().toString();
        userJobsUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("jobs").path("{?query,order,page,pageSize}").build().toString();
        followersUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("followers").path("{?page,pageSize}").build().toString();
        followingUrl = uriInfo.getBaseUriBuilder().path("users")
            .path("{id}").path("following").path("{?page,pageSize}").build().toString();
        jobsUrl = uriInfo.getBaseUriBuilder().path("jobs")
            .path("{?order,category,state,city,page,pageSize}").build().toString();

        statesUrl = uriInfo.getBaseUriBuilder().path("locations")
            .path("states").path("{stateId}").build().toString();
        citiesUrl = uriInfo.getBaseUriBuilder().path("locations")
            .path("states").path("{stateId}").path("cities").build().toString();
    }

    public String getUsersUrl() {
        return usersUrl;
    }

    public void setUsersUrl(String usersUrl) {
        this.usersUrl = usersUrl;
    }

    public String getJobsUrl() {
        return jobsUrl;
    }

    public void setJobsUrl(String jobsUrl) {
        this.jobsUrl = jobsUrl;
    }

    public String getCitiesUrl() {
        return citiesUrl;
    }

    public void setCitiesUrl(String citiesUrl) {
        this.citiesUrl = citiesUrl;
    }

    public String getStatesUrl() {
        return statesUrl;
    }

    public void setStatesUrl(String statesUrl) {
        this.statesUrl = statesUrl;
    }

    public String getNotificationsUrl() {
        return notificationsUrl;
    }

    public void setNotificationsUrl(String notificationsUrl) {
        this.notificationsUrl = notificationsUrl;
    }

    public String getSentRequestsUrl() {
        return sentRequestsUrl;
    }

    public void setSentRequestsUrl(String sentRequestsUrl) {
        this.sentRequestsUrl = sentRequestsUrl;
    }

    public String getReceivedRequestsUrl() {
        return receivedRequestsUrl;
    }

    public void setReceivedRequestsUrl(String receivedRequestsUrl) {
        this.receivedRequestsUrl = receivedRequestsUrl;
    }

    public String getUserJobsUrl() {
        return userJobsUrl;
    }

    public void setUserJobsUrl(String userJobsUrl) {
        this.userJobsUrl = userJobsUrl;
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
}
