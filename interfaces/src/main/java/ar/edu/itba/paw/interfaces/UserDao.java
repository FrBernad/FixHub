package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> get(String id);

    List<User> list();

    User createUser(String name, String password);
}