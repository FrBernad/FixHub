package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@XmlType(name = "")
public class JobContactDto {

    public static Collection<JobContactDto> mapContactToDto(Collection<JobContact> jobContacts, UriInfo uriInfo, SecurityContext securityContext) {
        return jobContacts.stream().map(jc -> new JobContactDto(jc, uriInfo,securityContext)).collect(Collectors.toList());
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
    private ContactInfoDto contactInfo;

    public JobContactDto() {
    }

    public JobContactDto(JobContact jobContact, UriInfo uriInfo, SecurityContext securityContext) {
        this.id = jobContact.getId();
        this.user = new UserDto(jobContact.getUser(), uriInfo, securityContext);
        this.provider = new UserDto(jobContact.getProvider(), uriInfo, securityContext);
        this.message = jobContact.getMessage();
        this.status = jobContact.getStatus();
        this.date = jobContact.getDate();
        this.jobUrl = JobDto.getJobUriBuilder(jobContact.getJob(), uriInfo).build().toString();
        this.jobProvided = jobContact.getJob().getJobProvided();
        this.jobId = jobContact.getJob().getId();
        this.contactInfo = new ContactInfoDto(jobContact.getContactInfo());
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

    public ContactInfoDto getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDto contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
