package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class JobContactDto {

    public static UriBuilder getContactUriBuilder(JobContact contact, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("user").path("jobs").path("requests").path(String.valueOf(contact.getId()));
    }

    public static Collection<JobContactDto> mapContactToDto(Collection<JobContact> jobContacts, UriInfo uriInfo) {
        return jobContacts.stream().map(jc -> new JobContactDto(jc, uriInfo)).collect(Collectors.toList());
    }

    private Long id;

    private UserDto user;
    private UserDto provider;
    private String message;

    private JobStatus status;

    private LocalDate date;

    private String jobUrl;
    private String jobProvided;
    private Long jobId;

    private String url;

    private ContactInfoDto contactInfo;

    public JobContactDto() {
    }

    public JobContactDto(JobContact jobContact, UriInfo uriInfo) {
        this.id = jobContact.getId();
        this.user = new UserDto(jobContact.getUser(), uriInfo);
        this.provider = new UserDto(jobContact.getProvider(), uriInfo);
        this.message = jobContact.getMessage();
        this.status = jobContact.getStatus();
        this.date = jobContact.getDate();
        this.jobUrl = JobDto.getJobUriBuilder(jobContact.getJob(), uriInfo).build().toString();
        this.jobProvided = jobContact.getJob().getJobProvided();
        this.jobId = jobContact.getJob().getId();
        this.contactInfo = new ContactInfoDto(jobContact.getContactInfo());
        this.url = uriInfo.getBaseUriBuilder().path("requests")
            .path(jobContact.getId().toString())
            .build().toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public ContactInfoDto getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDto contactInfo) {
        this.contactInfo = contactInfo;
    }
}
