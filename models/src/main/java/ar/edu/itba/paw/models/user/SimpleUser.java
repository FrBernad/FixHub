package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.ContactInfo;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.job.JobContact;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity(name = "SimpleUser")
@DiscriminatorValue("simple")
public class SimpleUser extends User {

    public SimpleUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) {
        super(password, name, surname, email, phoneNumber, state, city, roles);
    }

    /* default */
    protected SimpleUser() {
        // Just for Hibernate
    }

}
