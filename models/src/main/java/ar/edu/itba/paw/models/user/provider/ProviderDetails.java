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
    private Long jobsCount;

    @Transient
    private Long avgRating;

    @Transient
    private Long reviewCount;

    public ProviderDetails(Location location, Schedule schedule) {
        this.location = location;
        this.schedule = schedule;
    }

    protected ProviderDetails() {
    }

    public long getJobsCount() {
        return jobsCount;
    }

    public long getAvgRating() {
        return avgRating;
    }

    public long getReviewCount() {
        return reviewCount;
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

    public void setAvgRating(long avgRating) {
        this.avgRating = avgRating;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

}
