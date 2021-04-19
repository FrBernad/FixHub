package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.UserStats;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city) throws DuplicateUserException;

    Optional<User> verifyAccount(String token);

    void resendVerificationToken(User user);

    boolean validatePasswordReset(String token);

    void generateNewPassword(User user);

    Optional<User> updatePassword(String token, String password);

    Optional<UserStats> getUserStatsById(long id);

    Collection<ContactInfo> getContactInfo(User user);

    ContactInfo addContactInfo( User user, String state,String  city,String street,String addressNumber,String floor,String departmentNumber);

    void updateUserInfo(UserInfo userInfo, long userId);

    void makeProvider(long userId);

    void contact(Long providerId, User user, Long contactInfoId, String message, String state, String city, String street, String addressNumber, String floor, String departmentNumber);

    Collection<JobContact> getClients(Long providerId);
}
