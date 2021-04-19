package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobContact {
    private ContactInfo contactInfo;
    private ContactUser client;
    private String message,jobProvided;
    private LocalDateTime date;
    private Long jobId;
    private JobCategory category;

    public JobContact(ContactInfo contactInfo, ContactUser client, String message, LocalDateTime date,Long jobId, String jobProvided,JobCategory category) {
        this.contactInfo = contactInfo;
        this.client = client;
        this.message = message;
        this.date = date;
        this.jobId = jobId;
        this.jobProvided=jobProvided;
        this.category = category;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactUser getClient() {
        return client;
    }

    public void setClient(ContactUser client) {
        this.client = client;
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

    public LocalDateTime getDate() {
        return date;
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
