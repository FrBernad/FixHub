package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.job.Review;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class JobContactDto {

    public static Collection<JobContactDto> mapContactToDto(Collection<JobContact> jobContacts, UriInfo uriInfo) {
        return jobContacts.stream().map(jc -> new JobContactDto(jc, uriInfo)).collect(Collectors.toList());
    }

    private Long id;

    private UserDto user;
    private UserDto provider;
    private String message;
    private JobStatus status;
    private LocalDate date;
    String jobUrl;
    String jobProvided;
//    private ContactInfo contactInfo;

    public JobContactDto() {}

    public JobContactDto(JobContact jobContact, UriInfo uriInfo) {
        this.id = jobContact.getId();
        this.user = new UserDto(jobContact.getUser(),uriInfo);
        this.provider = new UserDto(jobContact.getProvider(),uriInfo);
        this.message = jobContact.getMessage();
        this.status = jobContact.getStatus();
        this.date = jobContact.getDate();
        this.jobUrl = JobDto.getJobUriBuilder(jobContact.getJob(),uriInfo).build().toString();
        this.jobProvided = jobContact.getJob().getJobProvided();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public UserDto getProvider() {
        return provider;
    }

    public void setProvider(UserDto provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }
}
