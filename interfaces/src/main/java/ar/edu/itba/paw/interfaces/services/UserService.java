package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserInfo;
import ar.edu.itba.paw.models.UserStats;
import ar.edu.itba.paw.models.VerificationToken;

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

    void updateUserInfo(UserInfo userInfo, long userId);

    void makeProvider(long userId);

}
