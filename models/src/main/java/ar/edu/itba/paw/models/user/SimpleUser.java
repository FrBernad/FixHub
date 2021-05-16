package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.Image;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "SimpleUser")
@DiscriminatorValue("simple")
public class SimpleUser extends User {

    public SimpleUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles, Image profileImage, Image coverImage) {
        super(password, name, surname, email, phoneNumber, state, city, roles, profileImage, coverImage);
    }

    /* default */
    protected SimpleUser() {
        // Just for Hibernate
    }

}
