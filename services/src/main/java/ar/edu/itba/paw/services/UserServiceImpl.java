package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User findById(String id) {
        return null;
    }

    public List<User> list() {
        return null;
    }

    @Override
    public User createUser(String name, String password) {
        return userDao.createUser(name, password);
    }
}