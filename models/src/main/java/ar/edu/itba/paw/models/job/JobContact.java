package ar.edu.itba.paw.models.job;

import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class JobContact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_seq")
    @SequenceGenerator(sequenceName = "contact_id_seq", name = "contact_id_seq", allocationSize = 1)
    @Column(name = "c_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_provider_id")
    private User provider;

    @Column(name = "c_message", length = 300, nullable = false)
    private String message;

    @Column(name = "c_status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Column(name = "c_date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_contact_info")
    private ContactInfo contactInfo;

    public JobContact(User user, User provider, ContactInfo contactInfo, String message, LocalDateTime date, Job job, JobStatus status) {
        this.user = user;
        this.provider = provider;
        this.contactInfo = contactInfo;
        this.message = message;
        this.date = date;
        this.job = job;
        this.status = status;
    }

    protected JobContact() {
    }

    public Date getContactDate() {
        return Date.from(Instant.from(date));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public boolean isWorkPending() {
        return this.status == JobStatus.PENDING;
    }

    public boolean isWorkDone() {
        return this.status == JobStatus.FINISHED;
    }

    public boolean isWorkRejected() {
        return this.status == JobStatus.REJECTED;
    }

    public boolean isWorkInProgress() {
        return this.status == JobStatus.IN_PROGRESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date.toLocalDate();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Job getJobId() {
        return job;
    }

    public void setJobId(Job job) {
        this.job = job;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobContact)) return false;
        JobContact that = (JobContact) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

