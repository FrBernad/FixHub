package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password,String name, String surname,  String email, String phoneNumber,String state,  String city) throws  DuplicateUserException;
}