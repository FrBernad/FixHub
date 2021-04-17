package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.UserInfo;
import ar.edu.itba.paw.models.UserStats;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) throws DuplicateUserException;

    Collection<Roles> getUserRoles();

    Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal);

    Optional<User> updatePassword(long userId, String password);

    Optional<UserStats> getUserStatsById(long id);

    void updateUserInfo(UserInfo userInfo, long userId);

    void addRole(long userId, Roles newRole);

}