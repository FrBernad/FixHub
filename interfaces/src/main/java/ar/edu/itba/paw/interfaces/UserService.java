package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserService {
    User findById(String id);

    List<User> list();

//    User createUser(String name, String password);

    User createUser(String password,String name, String surname,  String email, String phoneNumber,String state, String city);

}
