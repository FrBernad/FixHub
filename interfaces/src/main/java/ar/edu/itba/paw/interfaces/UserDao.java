package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(long id);

    Optional<User> getByEmail(String email);

    List<User> list();

    User createUser(String password,String name, String surname,  String email, String phoneNumber,String state,  String city);
//    User createUser(String name, String password);
}