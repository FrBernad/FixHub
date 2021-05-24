package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Stats;

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

    void updateUserInfo(UserInfo userInfo, User user);

    void contact(ContactDto contactDto, User user, User provider) throws IllegalContactException;

    void makeProvider(User user, List<Long> citiesId, String startTime, String endTime);

    void updateProviderInfo(User user,List<Long> citiesId,String startTime,String endTime);

    void updateCoverImage(ImageDto imageDto, User user);

    void updateProfileImage(ImageDto imageDto, User user);

    boolean hasContactJobProvided(User provider, User user);

    void followUser(User user, User follower);

    void unfollowUser(User user, User follower);

}
