package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Provider")
@DiscriminatorValue("provider")
public class Provider extends User {

    @OneToMany
    @JoinColumn(name = "j_provider_id")
    private Set<Job> jobs;

    @OneToOne
    @JoinColumn(name = "u_location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "u_schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "provider")
    private Set<JobContact> contacts;

    public Provider(String password, String name, String surname, String email, String phoneNumber, String state, String city, Set<Roles> roles,Location location, Schedule schedule) {
        super(password, name, surname, email, phoneNumber, state, city, roles);
        this.location = location;
        this.schedule = schedule;
    }

    /* default */
    protected Provider() {
        // Just for Hibernate
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<JobContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<JobContact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;
        if (!super.equals(o)) return false;
        Provider provider = (Provider) o;
        return Objects.equals(jobs, provider.jobs) && Objects.equals(location, provider.location) && Objects.equals(schedule, provider.schedule) && Objects.equals(contacts, provider.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobs, location, schedule, contacts);
    }

    @Override
    public String toString() {
        return "Provider{" +
            "jobs=" + jobs +
            ", location=" + location +
            ", schedule=" + schedule +
            ", contacts=" + contacts +
            '}';
    }
}

