package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    List<User> list();

//    User createUser(String name, String password);

    User createUser(String password,String name, String surname,  String email, String phoneNumber,String state, String city);

}
