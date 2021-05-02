package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.IllegalContactException;
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

    void updateUserInfo(UserInfo userInfo, User user);

    void contact(ContactDto contactDto) throws IllegalContactException;

    ProviderLocation getLocationByProviderId(Long providerId);

    void makeProvider(User user, List<Long> citiesId, String startTime, String endTime);

    void updateCoverImage(ImageDto imageDto, User user);

    void updateProfileImage(ImageDto imageDto, User user);

    Optional<UserSchedule> getScheduleByUserId(long userId);

    boolean hasContactJobProvided(Job job, User user);

    int getFollowersCount(Long userId);

    int getFollowingCount(Long userId);

    Collection<Integer> getAllUserFollowingsIds(Long userId);

    void followUserById(Long userId, Long followerId);

    void unfollowUserById(Long userId, Long followerId);

}
