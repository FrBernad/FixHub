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
import java.util.Set;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Set<Roles> roles) throws DuplicateUserException;

    Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal);

    Optional<User> updatePassword(long userId, String password);

    Optional<Stats> getUserStatsById(long id);

    void addRole(long userId, Roles newRole);

    ContactInfo addContactInfo(ContactDto contactDto);

    void addClient(ContactDto contactDto, Long contactInfoId, Timestamp time);

    Collection<JobContact> getClientsByProvider(User provider, int page, int itemsPerPage);

    int getClientsCountByProvider(User provider);

    Collection<JobContact> getProvidersByClient(User client, int page, int itemsPerPage);

    int getProvidersCountByClient(User client);

    void addSchedule(Long userId, String startTime, String endTime);

    void addLocation(Long userId, List<Long> citiesId);

    Collection<User> getUserFollowers(Long userId, int page, int itemsPerPage);

    Collection<User> getUserFollowings(Long userId, int page, int itemsPerPage);

    Integer getUserFollowersCount(Long userId);

    Integer getUserFollowingCount(Long userId);


}