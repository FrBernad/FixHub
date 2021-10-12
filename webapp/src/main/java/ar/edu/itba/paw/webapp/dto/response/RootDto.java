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
        final String baseUrl = uriInfo.getBaseUriBuilder().build().toString();

        usersUrl = baseUrl + "users/{id}";
        notificationsUrl = baseUrl+"users/{id}/notifications/{?onlyNew,page,pageSize}";
        sentRequestsUrl = baseUrl+"users/{id}/requests/sent/{?status,page,order,pageSize}";
        receivedRequestsUrl = baseUrl+"users/{id}/requests/received/{?status,page,order,pageSize}";
        userJobsUrl = baseUrl+"users/{id}/jobs/{?query,order,page,pageSize}";
        followersUrl = baseUrl+"users/{id}/followers/{?page,pageSize}";
        followingUrl = baseUrl+"users/{id}/following/{?page,pageSize}";
        jobsUrl = baseUrl+"jobs/{?order,category,state,city,page,pageSize}";

        statesUrl = baseUrl+"locations/states/{id}";
        citiesUrl = baseUrl+"locations/states/{id}/cities";
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
