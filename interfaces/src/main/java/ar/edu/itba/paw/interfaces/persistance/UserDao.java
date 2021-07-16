package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.models.contact.AuxContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.pagination.StatusOrderOptions;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.Schedule;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserDao {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Set<Roles> roles) throws DuplicateUserException;

    ContactInfo addContactInfo(AuxContactDto auxContactDto);

    JobContact createJobContact(User user, User provider, ContactInfo contactInfo, String message, LocalDateTime creationTime, Job job);

    Optional<ContactInfo> getContactInfoById(Long Id);

    Collection<JobContact> getClientsByProvider(User provider, JobStatus status, StatusOrderOptions order, int page, int itemsPerPage);

    int getClientsCountByProvider(User provider, JobStatus status);

    Collection<JobContact> getProvidersByClient(User client, JobStatus status, StatusOrderOptions order, int page, int itemsPerPage);

    int getProvidersCountByClient(User client, JobStatus status);

    Collection<User> getUserFollowers(User user, int page, int itemsPerPage);

    Collection<User> getUserFollowings(User user, int page, int itemsPerPage);

    Integer getUserFollowersCount(User user);

    Integer getUserFollowingCount(User user);

    void persistProviderDetails(Location location, Schedule schedule);

    boolean hasContactJobProvided(User provider, User user, Job job);

}