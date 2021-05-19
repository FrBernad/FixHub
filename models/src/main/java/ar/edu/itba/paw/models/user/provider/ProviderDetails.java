package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class ProviderDetails {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "j_provider_id")
    private Set<Job> jobs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_location_id")
    private Location location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private Set<JobContact> contacts;

    @Transient
    private long jobsCount = 0;

    @Transient
    private long avgRating = 0;

    @Transient
    private long reviewCount = 0;

    public ProviderDetails(Location location, Schedule schedule) {
        this.location = location;
        this.schedule = schedule;
    }

    protected ProviderDetails() {
    }

    public long getJobsCount() {
        return jobsCount;
    }


    public Set<Job> getJobs() {
        return jobs;
    }

    public Location getLocation() {
        return location;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Set<JobContact> getContacts() {
        return contacts;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setContacts(Set<JobContact> contacts) {
        this.contacts = contacts;
    }

    public void setJobsCount(long jobsCount) {
        this.jobsCount = jobsCount;
    }

    public long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(long avgRating) {
        this.avgRating = avgRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderDetails that = (ProviderDetails) o;
        return Objects.equals(jobs, that.jobs) && location.equals(that.location) && schedule.equals(that.schedule) && Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobs, location, schedule, contacts);
    }
}
