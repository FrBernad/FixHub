package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "Provider")
@DiscriminatorValue("provider")
public class Provider extends User {

    @OneToMany
    @JoinColumn(name = "j_provider_id")
    private Collection<Job> jobs;

    @OneToOne
    @JoinColumn(name = "u_location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "u_schedule_id")
    private Schedule schedule;

    public Provider(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles, Image profileImage, Image coverImage, Collection<Job> jobs, Location location, Schedule schedule) {
        super(password, name, surname, email, phoneNumber, state, city, roles, profileImage, coverImage);
        this.jobs = jobs;
        this.location = location;
        this.schedule = schedule;
    }

    /* default */
    protected Provider() {
        // Just for Hibernate
    }

}

