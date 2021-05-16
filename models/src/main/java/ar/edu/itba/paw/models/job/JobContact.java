package ar.edu.itba.paw.models.job;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Provider;

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
    private Long contactId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "c_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "c_provider_id")
    private Provider provider;

    @Column(name = "c_message", length = 300, nullable = false)
    private String message;

    @Column(name = "c_date", nullable = false)
    private LocalDateTime date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_job_id")
    private Job job;

    private JobCategory category;

    public JobContact(User user, Provider provider, String message, LocalDateTime date, Job job, JobCategory category) {
        this.user = user;
        this.provider = provider;
        this.message = message;
        this.date = date;
        this.job = job;
        this.category = category;
    }

    protected JobContact() {
    }

    public Date getContactDate() {
        return Date.from(Instant.from(date));
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

    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobContact)) return false;
        JobContact that = (JobContact) o;
        return Objects.equals(contactId, that.contactId)
            && Objects.equals(user, that.user)
            && Objects.equals(provider, that.provider)
            && Objects.equals(message, that.message)
            && Objects.equals(date, that.date)
            && Objects.equals(job, that.job)
            && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, user, provider, message, date, job, category);
    }

    @Override
    public String toString() {
        return "JobContact{" +
            "contactId=" + contactId +
            ", user=" + user +
            ", provider=" + provider +
            ", message='" + message + '\'' +
            ", date=" + date +
            ", job=" + job +
            ", category=" + category +
            '}';
    }
}

