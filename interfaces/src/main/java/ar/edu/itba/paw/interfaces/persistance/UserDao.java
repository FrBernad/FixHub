package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Roles;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) throws DuplicateUserException;

    Collection<Roles> getUserRoles();

    Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal);

}