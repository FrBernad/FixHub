package ar.edu.itba.paw.webapp.dto.customValidations;

import ar.edu.itba.paw.models.user.provider.ProviderDetails;
import ar.edu.itba.paw.webapp.dto.response.LocationDto;
import ar.edu.itba.paw.webapp.dto.response.ScheduleDto;

import javax.ws.rs.core.UriInfo;

public class ProviderDetailsDto {

    private LocationDto location;
    private ScheduleDto schedule;

    private Long jobsCount;
    private Long avgRating;
    private Long reviewCount;
    private Integer contactsCount;

    public ProviderDetailsDto() {
    }

    public ProviderDetailsDto(ProviderDetails providerDetails, UriInfo uriInfo) {
        this.location = new LocationDto(providerDetails.getLocation(), uriInfo);
        this.schedule = new ScheduleDto(providerDetails.getSchedule());
        if (providerDetails.getJobsCount() != null) {
            this.jobsCount = providerDetails.getJobsCount();
        }
        if (providerDetails.getAvgRating() != null) {
            this.avgRating = providerDetails.getAvgRating();
        }
        if (providerDetails.getReviewCount() != null) {
            this.reviewCount = providerDetails.getReviewCount();
        }
        if (providerDetails.getContacts() != null) {
            this.contactsCount = providerDetails.getContacts().size();
        }
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }

    public Long getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(Long jobsCount) {
        this.jobsCount = jobsCount;
    }

    public Long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Long avgRating) {
        this.avgRating = avgRating;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getContactsCount() {
        return contactsCount;
    }

    public void setContactsCount(Integer contactsCount) {
        this.contactsCount = contactsCount;
    }
}


