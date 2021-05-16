package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.Schedule;
import ar.edu.itba.paw.models.user.provider.Stats;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) throws DuplicateUserException;

    Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal);

    Optional<User> updatePassword(long userId, String password);

    Optional<Stats> getUserStatsById(long id);

    void updateUserInfo(UserInfo userInfo, User user);

    void addRole(long userId, Roles newRole);

    ContactInfo addContactInfo(ContactDto contactDto);

    Collection<ContactInfo> getContactInfo(User user);

    Optional<ContactInfo> getContactInfoById(Long contactInfoId);

    void addClient(ContactDto contactDto, Long contactInfoId, Timestamp time);

    Collection<JobContact> getClientsByProviderId(Long providerId, int page, int itemsPerPage);

    int getClientsCountByProviderId(Long providerId);

    Collection<JobContact> getProvidersByClientId(Long clientId, int page, int itemsPerPage);

    int getProvidersCountByClientId(Long clientId);

    void addSchedule(Long userId, String startTime, String endTime);

    void addLocation(Long userId, List<Long> citiesId);

    Location getLocationByProviderId(Long providerId);

    void updateCoverImage(Long imageId, User user);

    void updateProfileImage(Long imageId, User user);

    Optional<Schedule> getScheduleByUserId(long userId);

    boolean hasContactJobProvided(Job job, User user);

    Collection<User> getUserFollowers(Long userId, int page, int itemsPerPage);

    Collection<User> getUserFollowings(Long userId, int page, int itemsPerPage);

    Collection<User> getAllUserFollowers(Long userId);

    Collection<Integer> getAllUserFollowingsIds(Long userId);

    Collection<Integer> getAllUserFollowersIds(Long userId);

    Integer getUserFollowersCount(Long userId);

    Integer getUserFollowingCount(Long userId);


}