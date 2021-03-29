package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public Optional<User> findById(long id) {
        return userDao.getById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public List<User> list() {
        return null;
    }

    @Override
    public User createUser(String password,String name, String surname,  String email, String phoneNumber,String state,  String city){
        return userDao.createUser(password,name,surname,email,phoneNumber,state,city);
    }


//    @Override
//    public User createUser(String name, String password) {
//        return userDao.createUser(name, password);
//    }
}