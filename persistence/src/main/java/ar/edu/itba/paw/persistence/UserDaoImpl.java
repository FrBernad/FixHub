package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.contact.NewContactDto;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.job.JobStatus;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.ProviderDetails;
import ar.edu.itba.paw.models.user.provider.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        final String hqlQuery =
            " SELECT u, count(DISTINCT j.id) AS total_jobs, count(r.rating) AS total_reviews, coalesce(avg(r.rating),0) as avg_rating " +
                " FROM User u " +
                " LEFT OUTER JOIN " +
                " u.providerDetails.jobs j" +
                " LEFT OUTER JOIN " +
                " j.reviews r " +
                " WHERE u.email = :email " +
                " group by u.id";

        final Collection<Object[]> hqlQueryResult = em.createQuery(hqlQuery, Object[].class)
            .setParameter("email", email)
            .getResultList();

        return hqlQueryResult
            .stream()
            .map(objArray -> {
                final User user = (User) objArray[0];
                if (user.hasRole(Roles.PROVIDER)) {
                    final ProviderDetails providerDetails = user.getProviderDetails();
                    providerDetails.setJobsCount((Long) objArray[1]);
                    providerDetails.setReviewCount((Long) objArray[2]);
                    providerDetails.setAvgRating(Math.round((Double) objArray[3]));
                }
                return user;
            }).findFirst();
    }

    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Set<Roles> roles) throws DuplicateUserException {

        Collection<User> userCollection = em.createQuery("from User as u where u.email = :email", User.class)
            .setParameter("email", email)
            .getResultList();

        if (!userCollection.isEmpty()) {
            throw new DuplicateUserException();
        }

        final User user = new User(password, name, surname, email, phoneNumber, state, city, roles);

        em.persist(user);

        return user;
    }

    @Override
    public ContactInfo addContactInfo(NewContactDto newContactDto) {

        final ContactInfo contactInfo = new ContactInfo(newContactDto.getUser(), newContactDto.getState(),
            newContactDto.getCity(), newContactDto.getStreet(),
            newContactDto.getAddressNumber(), newContactDto.getFloor(),
            newContactDto.getDepartmentNumber());

        em.persist(contactInfo);

        return contactInfo;
    }


    @Override
    public boolean hasContactJobProvided(User provider, User user, Job job) {
        final Query query = em.createQuery("FROM JobContact as c where c.provider.id = :providerId and c.user.id = :userId and c.job.id = :jobId");
        query.setParameter("providerId", provider.getId());
        query.setParameter("userId", user.getId());
        query.setParameter("jobId", job.getId());
        return query.getResultList().size() > 0;

    }

    @Override
    public JobContact createJobContact(User user, User provider, ContactInfo contactInfo, String message, LocalDateTime creationTime, Job job) {
        final JobContact jobContact = new JobContact(user, provider, contactInfo, message, creationTime, job, JobStatus.PENDING);
        em.persist(jobContact);
        return jobContact;
    }

    @Override
    public Optional<ContactInfo> getContactInfoById(Long id) {
        return Optional.ofNullable(em.find(ContactInfo.class, id));
    }

    @Override
    public Collection<JobContact> getClientsByProvider(User provider, JobStatus status, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();

        variables.add(provider.getId());

        String statusQuery = getClientsByProviderStatusQuery(status, variables);

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " SELECT c_id " +
                " FROM CONTACT " +
                " WHERE c_provider_id = ? " + statusQuery +
                " ORDER BY c_date DESC " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from JobContact jb where id IN :filteredIds order by jb.date DESC", JobContact.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public int getClientsCountByProvider(User provider, JobStatus status) {
        List<Object> variables = new LinkedList<>();
        variables.add(provider.getId());

        String statusQuery = getClientsByProviderStatusQuery(status, variables);

        final String query = String.format("SELECT count(c_id) total FROM CONTACT WHERE c_provider_id = ? %s", statusQuery);

        Query nativeQuery = em.createNativeQuery(query);

        setQueryVariables(nativeQuery, variables);

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }


    private String getClientsByProviderStatusQuery(JobStatus status, List<Object> variables) {
        String statusQuery = "";
        if (status != null) {
            statusQuery = " AND c_status = ? ";
            variables.add(status.name());
        }
        return statusQuery;
    }

    @Override
    public Collection<JobContact> getProvidersByClient(User client, int page, int itemsPerPage) {

        final List<Object> variables = new LinkedList<>();

        variables.add(client.getId());

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " SELECT c_id " +
                " FROM CONTACT " +
                " WHERE c_user_id = ? " +
                " ORDER BY c_date DESC " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from JobContact jb where id IN :filteredIds order by jb.date DESC", JobContact.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

    }

    @Override
    public int getProvidersCountByClient(User client) {
        final String query = "SELECT count(c_id) total FROM CONTACT WHERE c_user_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, client.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Collection<User> getUserFollowers(User user, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();

        variables.add(user.getId());

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " select u_id " +
                " from USERS " +
                " JOIN " +
                " (SELECT f_user_id FROM FOLLOWS WHERE f_followed_user_id = ?) followedIds " +
                " on f_user_id = users.u_id " +
                " order by u_id desc " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from User u where id IN :filteredIds order by u.id desc", User.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public Collection<User> getUserFollowings(User user, int page, int itemsPerPage) {

        final List<Object> variables = new LinkedList<>();

        variables.add(user.getId());

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " select u_id " +
                " from USERS " +
                " JOIN " +
                " (SELECT f_followed_user_id FROM FOLLOWS WHERE f_user_id = ?) followedIds " +
                " on f_followed_user_id = users.u_id " +
                " order by u_id desc " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from User u where id IN :filteredIds order by u.id desc", User.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

    }

    @Override
    public Integer getUserFollowersCount(User user) {
        final String query = "SELECT count(f_user_id) total FROM FOLLOWS WHERE f_followed_user_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, user.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Integer getUserFollowingCount(User user) {
        final String query = "SELECT count(f_followed_user_id) total FROM FOLLOWS WHERE f_user_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, user.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public void persistProviderDetails(Location location, Schedule schedule) {
        em.persist(location);
        em.persist(schedule);
    }

    private void setQueryVariables(Query query, Collection<Object> variables) {
        int i = 1;
        for (Object variable : variables) {
            query.setParameter(i, variable);
            i++;
        }
    }

    private String getOffsetAndLimitQuery(int page, int itemsPerPage, List<Object> variables) {
        final StringBuilder offsetAndLimitQuery = new StringBuilder();
        if (page > 0) {
            offsetAndLimitQuery.append(" OFFSET ? ");
            variables.add(page * itemsPerPage);
        }
        if (itemsPerPage > 0) {
            offsetAndLimitQuery.append(" LIMIT ? ");
            variables.add(itemsPerPage);
        }
        return offsetAndLimitQuery.toString();
    }


}