package ar.edu.itba.paw.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class JobContact {
    private ContactInfo contactInfo;
    private ContactUser user;
    private String message,jobProvided;
    private LocalDateTime date;
    private Long jobId;
    private JobCategory category;

    public JobContact(ContactInfo contactInfo, ContactUser user, String message, LocalDateTime date,Long jobId, String jobProvided,JobCategory category) {
        this.contactInfo = contactInfo;
        this.user = user;
        this.message = message;
        this.date = date;
        this.jobId = jobId;
        this.jobProvided=jobProvided;
        this.category = category;
    }

    public Date getContactDate(){
        return Date.from(Instant.from(date));
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactUser getUser() {
        return user;
    }

    public void setUser(ContactUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobProvided() {
        return jobProvided;
    }

    public void setJobProvided(String jobProvided) {
        this.jobProvided = jobProvided;
    }

    public LocalDate getDate() {
        return date.toLocalDate();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }
}
