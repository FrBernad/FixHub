package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.UserStats;

import java.sql.Timestamp;
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

    ContactInfo addContactInfo( User user, String state,String  city,String street,String addressNumber,String floor,String departmentNumber);

    Collection<ContactInfo> getContactInfo(User user);

    Optional<ContactInfo> getContactInfoById(Long contactInfoId);

    void addClient(Long providerId, User user, Long contactInfoId, String message, Timestamp time);

    Collection<JobContact> getClients(Long providerId);
}