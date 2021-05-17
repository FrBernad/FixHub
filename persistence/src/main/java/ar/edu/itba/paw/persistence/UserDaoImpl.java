package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.job.Job;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.SimpleUser;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.provider.Location;
import ar.edu.itba.paw.models.user.provider.Schedule;
import ar.edu.itba.paw.models.user.provider.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.sql.Timestamp;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<User> q = cb.createQuery(User.class);

        Root<User> c = q.from(User.class);

        q.select(c);
        q.where(cb.equal(c.get("email"), email));

        return em.createQuery(q).getResultList().stream().findFirst();
    }

    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Set<Roles> roles) throws DuplicateUserException {

        Collection<User> userCollection = em.createQuery("from User as u where u.email = :email", User.class)
            .setParameter("email", email)
            .getResultList();

        if (!userCollection.isEmpty()) {
            throw new DuplicateUserException();
        }

        final User user = new SimpleUser(password, name, surname, email, phoneNumber, state, city, roles);

        em.persist(user);

        return user;
    }

    @Override
    public Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal) {
        final User user = getUserById(userId).orElseThrow(UserNotFoundException::new);
        user.removeRole(oldVal);
        user.addRole(newVal);

        return Optional.of(em.merge(user));
    }

    @Override
    public Optional<User> updatePassword(long userId, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<Stats> getUserStatsById(long id) {
        return Optional.empty();
    }

    @Override
    public void addRole(long userId, Roles newRole) {

    }

    @Override
    public ContactInfo addContactInfo(ContactDto contactDto) {
        return null;
    }

    @Override
    public Optional<ContactInfo> getContactInfoById(Long contactInfoId) {
        return Optional.empty();
    }

    @Override
    public void addClient(ContactDto contactDto, Long contactInfoId, Timestamp time) {

    }

    @Override
    public Collection<JobContact> getClientsByProvider(User provider, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();

        variables.add(provider.getId());

        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);

        final String filteredIdsSelectQuery =
            " SELECT c_id " +
                " FROM CONTACT " +
                " WHERE c_provider_id = ? " +
                " ORDER BY c_date DESC " + offsetAndLimitQuery;

        Query filteredIdsSelectNativeQuery = em.createNativeQuery(filteredIdsSelectQuery);

        setQueryVariables(filteredIdsSelectNativeQuery, variables);

        @SuppressWarnings("unchecked") final List<Long> filteredIds = ((List<Number>) filteredIdsSelectNativeQuery.getResultList()).stream().map(Number::longValue).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return Collections.emptyList();

        return em.createQuery("from JobContact where id IN :filteredIds", JobContact.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public int getClientsCountByProvider(User provider) {
        final String query = "SELECT count(c_id) total FROM CONTACT WHERE c_provider_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, provider.getId());

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
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

        return em.createQuery("from JobContact where id IN :filteredIds", JobContact.class)
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
    public void addSchedule(Long userId, String startTime, String endTime) {

    }

    @Override
    public void addLocation(Long userId, List<Long> citiesId) {

    }

    @Override
    public Location getLocationByProviderId(Long providerId) {
        return null;
    }

    @Override
    public Optional<Schedule> getScheduleByUserId(long userId) {
        return Optional.empty();
    }

    @Override
    public boolean hasContactJobProvided(Job job, User user) {
        return false;
    }

    @Override
    public Collection<User> getUserFollowers(Long userId, int page, int itemsPerPage) {
        final List<Object> variables = new LinkedList<>();

        variables.add(userId);

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

        return em.createQuery("from User where id IN :filteredIds", User.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();
    }

    @Override
    public Collection<User> getUserFollowings(Long userId, int page, int itemsPerPage) {

        final List<Object> variables = new LinkedList<>();

        variables.add(userId);

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

        return em.createQuery("from User where id IN :filteredIds", User.class)
            .setParameter("filteredIds", filteredIds)
            .getResultList();

    }

    @Override
    public Integer getUserFollowersCount(Long userId) {
        final String query = "SELECT count(f_user_id) total FROM FOLLOWS WHERE f_followed_user_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, userId);

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Integer getUserFollowingCount(Long userId) {
        final String query = "SELECT count(f_followed_user_id) total FROM FOLLOWS WHERE f_user_id = ?";

        Query nativeQuery = em.createNativeQuery(query);

        nativeQuery.setParameter(1, userId);

        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    private void setQueryVariables(Query query, Collection<Object> variables) {
        int i = 1;
        for (Object variable : variables) {
            query.setParameter(i, variable);
            i++;
        }
    }
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert userSimpleJdbcInsert;
//    private final SimpleJdbcInsert roleSimpleJdbcInsert;
//    private final SimpleJdbcInsert contactInfoSimpleJdbcInsert;
//    private final SimpleJdbcInsert contactProviderSimpleJdbcInsert;
//    private final SimpleJdbcInsert userScheduleSimpleJdbcInsert;
//    private final SimpleJdbcInsert userLocationSimpleJdbcInsert;
//    private static final RowMapper<Schedule> USER_SCHEDULE_ROW_MAPPER = (rs, rowNum) ->
//        new Schedule(rs.getString("us_start_time"), rs.getString("us_end_time"));
//
//    private static final ResultSetExtractor<Collection<Stats>> USER_STATS_ROW_MAPPER = rs -> {
//        Map<Long, Stats> statsMap = new HashMap<>();
//
//        long userId;
//
//        while (rs.next()) {
//
//            userId = rs.getLong("u_id");
//
//            if (!statsMap.containsKey(userId)) {
//                statsMap.put(userId, new Stats(
//                    rs.getLong("totalJobs"),
//                    rs.getLong("avgRating"),
//                    rs.getLong("totalReviews")));
//            }
//        }
//
//        return statsMap.values();
//    };
//
//    private static final ResultSetExtractor<Collection<User>> USER_RS_EXTRACTOR = rs -> {
//        Map<Long, User> userMap = new HashMap<>();
//
//        long userId;
//
//        while (rs.next()) {
//
//            userId = rs.getLong("u_id");
//
//            if (!userMap.containsKey(userId)) {
//                userMap.put(userId, new User(userId,
//                    rs.getString("u_password"),
//                    rs.getString("u_name"),
//                    rs.getString("u_surname"),
//                    rs.getString("u_email"),
//                    rs.getString("u_phone_number"),
//                    rs.getString("u_state"),
//                    rs.getString("u_city"),
//                    new HashSet<>(),
//                    rs.getLong("u_profile_picture"),
//                    rs.getLong("u_cover_picture")));
//            }
//
//            userMap.get(userId).addRole(Roles.valueOf(rs.getString("r_role")));
//        }
//
//        return userMap.values();
//    };
//
//    private final ResultSetExtractor<Collection<ContactInfo>> CONTACT_INFO_ROW_MAPPER = rs -> {
//        List<ContactInfo> contactInfo = new LinkedList<>();
//        while (rs.next()) {
//            contactInfo.add(new ContactInfo(
//                rs.getLong("ci_id"),
//                rs.getLong("ci_user_id"),
//                rs.getString("ci_state"),
//                rs.getString("ci_city"),
//                rs.getString("ci_street"),
//                rs.getString("ci_address_number"),
//                rs.getString("ci_floor"),
//                rs.getString("ci_department_number")));
//        }
//        return contactInfo;
//    };
//
//    private static final ResultSetExtractor<Collection<JobContact>> CLIENT_ROW_MAPPER = rs -> {
//        List<JobContact> contacts = new LinkedList<>();
//        while (rs.next()) {
//            ContactUser client = new ContactUser(rs.getLong("c_user_id"), rs.getString("u_name"), rs.getString("u_surname"), rs.getString("u_phone_number"), rs.getString("u_email"));
//            ContactInfo contactInfo = new ContactInfo(rs.getLong("ci_id"), rs.getLong("ci_user_id"), rs.getString("ci_state"), rs.getString("ci_city"), rs.getString("ci_street"), rs.getString("ci_address_number"), rs.getString("ci_floor"), rs.getString("ci_department_number"));
//            contacts.add(new JobContact(contactInfo, client, rs.getString("c_message"), rs.getTimestamp("c_date").toLocalDateTime(), rs.getLong("j_id"), rs.getString("j_job_provided"), JobCategory.valueOf(rs.getString("j_category"))));
//        }
//        return contacts;
//    };
//
//    private static final ResultSetExtractor<Collection<JobContact>> PROVIDER_ROW_MAPPER = rs -> {
//        List<JobContact> contacts = new LinkedList<>();
//        while (rs.next()) {
//            ContactUser provider = new ContactUser(rs.getLong("c_provider_id"), rs.getString("u_name"), rs.getString("u_surname"), rs.getString("u_phone_number"), rs.getString("u_email"));
//            ContactInfo contactInfo = new ContactInfo(rs.getLong("ci_id"), rs.getLong("ci_user_id"), rs.getString("ci_state"), rs.getString("ci_city"), rs.getString("ci_street"), rs.getString("ci_address_number"), rs.getString("ci_floor"), rs.getString("ci_department_number"));
//            contacts.add(new JobContact(contactInfo, provider, rs.getString("c_message"), rs.getTimestamp("c_date").toLocalDateTime(), rs.getLong("j_id"), rs.getString("j_job_provided"), JobCategory.valueOf(rs.getString("j_category"))));
//        }
//        return contacts;
//    };
//
//    private final Collection<Roles> roles = Arrays.asList(Roles.values().clone());
//
//    @Autowired
//    public UserDaoImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        userSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("u_id");
//        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("ROLES").usingGeneratedKeyColumns("r_id");
//        contactInfoSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("CONTACT_INFO").usingGeneratedKeyColumns("ci_id");
//        contactProviderSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("CONTACT").usingGeneratedKeyColumns("c_id");
//        userScheduleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USER_SCHEDULE");
//        userLocationSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USER_LOCATION");
//    }
//
//    @Override
//    public Optional<User> getUserById(long id) {
//        final String query = "SELECT * FROM USERS u JOIN ROLES r on u_id=r_user_id WHERE u_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.
//            query(query, new Object[]{id}, USER_RS_EXTRACTOR)
//            .stream()
//            .findFirst();
//    }
//
//    @Override
//    public Optional<User> getUserByEmail(String email) {
//        final String query = "SELECT * FROM (SELECT * FROM USERS WHERE u_email = ?) as AUX JOIN roles r on u_id = r_user_id";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{email}, USER_RS_EXTRACTOR)
//            .stream()
//            .findFirst();
//    }
//
//    @Override
//    public Collection<Roles> getUserRoles() {
//        return roles;
//    }
//
//    @Override
//    public Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal) {
//        final String query = "UPDATE roles set r_role = ? where r_user_id = ? and r_role = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, newVal.name(), userId, oldVal.name()) == 1) {
//            LOGGER.debug("Roles updated");
//            return getUserById(userId);
//        }
//        LOGGER.debug("Roles not updated");
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> updatePassword(long userId, String password) {
//        final String query = "UPDATE USERS set u_password = ? where u_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, password, userId) == 1) {
//            LOGGER.debug("Password updated");
//            return getUserById(userId);
//        }
//        LOGGER.debug("Password not updated");
//        return Optional.empty();
//    }
//
//    @Override
//    public void addRole(long userId, Roles newRole) {
//        final Map<String, Object> userRoles = new HashMap<>();
//        userRoles.put("r_user_id", userId);
//
//        userRoles.put("r_role", newRole.name());
//
//        roleSimpleJdbcInsert.execute(userRoles);
//        LOGGER.info("Added role {} to user {}", newRole.name(), userId);
//    }
//
//    @Override
//    public Optional<Stats> getUserStatsById(long id) {
//        final String query = "SELECT u_id, count(DISTINCT j_id) AS totalJobs,count(r_rating) AS totalReviews,avg(r_rating) AS avgRating" +
//            " FROM ((SELECT * FROM jobs LEFT OUTER JOIN reviews ON j_id = r_job_id) aux1 RIGHT OUTER JOIN " +
//            " (SELECT * FROM users WHERE u_id = ? ) aux2 ON u_id=j_provider_id) GROUP BY u_id";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, USER_STATS_ROW_MAPPER, new Object[]{id})
//            .stream().findFirst();
//    }
//
//
//    @Override
//    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) throws DuplicateUserException {
//        final Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("u_password", password);
//        userInfo.put("u_name", name);
//        userInfo.put("u_surname", surname);
//        userInfo.put("u_email", email);
//        userInfo.put("u_phone_number", phoneNumber);
//        userInfo.put("u_state", state);
//        userInfo.put("u_city", city);
//        userInfo.put("u_profile_picture", null);
//
//        final Number id;
//
//        try {
//            id = userSimpleJdbcInsert.executeAndReturnKey(userInfo);
//            LOGGER.info("Created user with id {}", id);
//        } catch (org.springframework.dao.DuplicateKeyException e) {
//            throw new DuplicateUserException();
//        }
//
//        final Map<String, Object> userRoles = new HashMap<>();
//        userRoles.put("r_user_id", id);
//
//        for (Roles role : roles) {
//            userRoles.put("r_role", role.name());
//            roleSimpleJdbcInsert.execute(userRoles);
//            LOGGER.info("Added role {} to user {}", role.name(), id);
//        }
//
//        return new User(id.longValue(), password, name, surname, email, phoneNumber, state, city, roles, null, null);
//    }
//
//    public Collection<ContactInfo> getContactInfo(User user) {
//        final String query = "SELECT * FROM CONTACT_INFO WHERE ci_user_id = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{user.getId()}, CONTACT_INFO_ROW_MAPPER);
//    }
//
//    @Override
//    public ContactInfo addContactInfo(ContactDto contact) {
//        final Map<String, Object> contactInfo = new HashMap<>();
//        contactInfo.put("ci_user_id", contact.getUser().getId());
//        contactInfo.put("ci_city", contact.getCity());
//        contactInfo.put("ci_state", contact.getState());
//        contactInfo.put("ci_street", contact.getStreet());
//        contactInfo.put("ci_floor", contact.getFloor());
//        contactInfo.put("ci_address_number", contact.getAddressNumber());
//        contactInfo.put("ci_department_number", contact.getDepartmentNumber());
//        final Number id = contactInfoSimpleJdbcInsert.executeAndReturnKey(contactInfo);
//        LOGGER.debug("Created new contact info with id {}", id);
//        return new ContactInfo(id.longValue(), contact.getUser().getId(), contact.getState(), contact.getCity(), contact.getStreet(), contact.getAddressNumber(), contact.getFloor(), contact.getDepartmentNumber());
//    }
//
//    public Optional<ContactInfo> getContactInfoById(Long contactInfoId) {
//        final String query = "SELECT * FROM CONTACT_INFO WHERE ci_id = ? ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{contactInfoId}, CONTACT_INFO_ROW_MAPPER)
//            .stream().findFirst();
//    }
//
//    @Override
//    public void addClient(ContactDto contactDto, Long contactInfoId, Timestamp time) {
//        final Map<String, Object> contactMap = new HashMap<>();
//        contactMap.put("c_provider_id", contactDto.getJob().getProvider().getId());
//        contactMap.put("c_job_id", contactDto.getJob().getId());
//        contactMap.put("c_user_id", contactDto.getUser().getId());
//        contactMap.put("c_info_id", contactInfoId);
//        contactMap.put("c_message", contactDto.getMessage());
//        contactMap.put("c_date", time.toLocalDateTime());
//        LOGGER.debug("Created new client");
//        contactProviderSimpleJdbcInsert.execute(contactMap);
//    }
//
//    @Override
//    public Collection<JobContact> getClientsByProviderId(Long providerId, int page, int itemsPerPage) {
//        final List<Object> variables = new LinkedList<>();
//
//        variables.add(providerId);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String query = "SELECT * FROM (SELECT * FROM " +
//            "(SELECT * FROM CONTACT JOIN CONTACT_INFO on c_info_id = ci_id where c_provider_id = ?)" +
//            " AUX JOIN USERS on u_id = c_user_id) AUX2 JOIN JOBS on j_id = c_job_id ORDER BY c_date DESC, c_id DESC " + offsetAndLimitQuery;
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.query(query, variables.toArray(), CLIENT_ROW_MAPPER);
//    }
//
//    @Override
//    public int getClientsCountByProviderId(Long providerId) {
//        final String query = "SELECT count(c_provider_id) as total FROM contact WHERE  c_provider_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{providerId},
//            (rs, num) -> rs.getInt("total")).stream().findFirst().orElse(0);
//    }
//
//    @Override
//    public Collection<JobContact> getProvidersByClientId(Long clientId, int page, int itemsPerPage) {
//        final List<Object> variables = new LinkedList<>();
//
//        variables.add(clientId);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String query = "SELECT * FROM (SELECT * FROM " +
//            "(SELECT * FROM CONTACT JOIN CONTACT_INFO on c_info_id = ci_id where c_user_id = ? ) AUX " +
//            "JOIN USERS on u_id = c_provider_id) AUX2 JOIN JOBS on j_id = c_job_id ORDER BY c_date DESC" +
//            offsetAndLimitQuery;
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.query(query, variables.toArray(), PROVIDER_ROW_MAPPER);
//    }
//
//    @Override
//    public int getProvidersCountByClientId(Long clientId) {
//        final String query = "SELECT count(c_user_id) as total FROM contact WHERE  c_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{clientId},
//            (rs, num) -> rs.getInt("total")).stream().findFirst().orElse(0);
//    }
//
//    @Override
//    public void addSchedule(Long userId, String startTime, String endTime) {
//        final Map<String, Object> schedule = new HashMap<>();
//
//        schedule.put("us_user_id", userId);
//        schedule.put("us_start_time", startTime);
//        schedule.put("us_end_time", endTime);
//        userScheduleSimpleJdbcInsert.execute(schedule);
//        LOGGER.info("Inserted schedule for user {}", userId);
//    }
//
//
//    @Override
//    public void addLocation(Long userId, List<Long> citiesId) {
//        final Map<String, Object> location = new HashMap<>();
//        for (Long cityId : citiesId) {
//            location.put("ul_user_id", userId);
//            location.put("ul_city_id", cityId);
//            userLocationSimpleJdbcInsert.execute(location);
//            LOGGER.info("Inserted location for user {}", userId);
//        }
//    }
//
//    @Override
//    public Location getLocationByProviderId(Long providerId) {
//        final String query = "SELECT * FROM (SELECT * FROM USER_LOCATION JOIN CITIES on ul_city_id = c_id where ul_user_id = ?) AUX JOIN states on c_state_id = s_id" +
//            " ORDER BY c_name ";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{providerId}, (rs) -> {
//            Location location = null;
//            while (rs.next()) {
//                if (location == null) {
//                    location = new Location(rs.getLong("ul_user_id"), new LinkedList<>(), rs.getString("s_name"));
//                }
//                location.getCities().add(new City(rs.getLong("ul_city_id"), rs.getString("c_name")));
//            }
//            return location;
//        });
//    }
//
//    @Override
//    public void updateProfileImage(Long imageId, User user) {
//        final String query = "UPDATE USERS set u_profile_picture = ? where u_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, imageId, user.getId()) == 1)
//            LOGGER.debug("Profile picture of user {} updated", user.getId());
//        else
//            LOGGER.debug("Profile picture of user {} not updated", user.getId());
//    }
//
//    @Override
//    public Optional<Schedule> getScheduleByUserId(long userId) {
//        final String query = "SELECT * FROM USER_SCHEDULE WHERE us_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId}, USER_SCHEDULE_ROW_MAPPER).stream().findFirst();
//    }
//
//    @Override
//    public boolean hasContactJobProvided(Job job, User user) {
//        return jdbcTemplate.query("SELECT count(*) as aux FROM CONTACT where c_job_id = ? and c_user_id = ?",
//            new Object[]{job.getId(), user.getId()},
//            (rs, rowNum) -> rs.getInt("aux"))
//            .stream().findFirst().orElse(0) > 0;
//    }
//
//    @Override
//    public void updateCoverImage(Long imageId, User user) {
//        final String query = "UPDATE USERS set u_cover_picture = ? where u_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        if (jdbcTemplate.update(query, imageId, user.getId()) == 1)
//            LOGGER.debug("Profile cover of user {} updated", user.getId());
//        else
//            LOGGER.debug("Profile cover of user {} not updated", user.getId());
//    }
//
//    @Override
//    public Collection<User> getUserFollowers(Long userId, int page, int itemsPerPage) {
//        final List<Object> variables = new LinkedList<>();
//
//        variables.add(userId);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String USER_WHERE_IDS_QUERY =
//            " where u_id in (select u_id " +
//                " from USERS " +
//                " JOIN " +
//                " (SELECT f_user_id FROM FOLLOWS WHERE f_followed_user_id = ?) followersIds " +
//                " on f_user_id = users.u_id " +
//                " order by u_id desc " + offsetAndLimitQuery + " ) ";
//
//        final String query =
//            " SELECT * FROM " +
//                " (SELECT * FROM USERS " + USER_WHERE_IDS_QUERY + " ) u " +
//                " JOIN " +
//                " ROLES on u.u_id = roles.r_user_id " +
//                " order by u_id desc ";
//
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.
//            query(query, USER_RS_EXTRACTOR, variables.toArray());
//    }
//
//    @Override
//    public Collection<User> getAllUserFollowers(Long userId) {
//        final String USER_WHERE_IDS_QUERY =
//            " where u_id in (select u_id " +
//                " from USERS " +
//                " JOIN " +
//                " (SELECT f_user_id FROM FOLLOWS WHERE f_followed_user_id = ?) followersIds " +
//                " on f_user_id = users.u_id " +
//                " order by u_id desc ) ";
//
//        final String query =
//            " SELECT * FROM " +
//                " (SELECT * FROM USERS " + USER_WHERE_IDS_QUERY + " ) u" +
//                " JOIN " +
//                " ROLES on u.u_id = roles.r_user_id ";
//
//        return jdbcTemplate.
//            query(query, USER_RS_EXTRACTOR, userId);
//    }
//
//    @Override
//    public Collection<User> getUserFollowings(Long userId, int page, int itemsPerPage) {
//        final List<Object> variables = new LinkedList<>();
//
//        variables.add(userId);
//
//        final String offsetAndLimitQuery = getOffsetAndLimitQuery(page, itemsPerPage, variables);
//
//        final String USER_WHERE_IDS_QUERY =
//            " where u_id in (select u_id " +
//                " from USERS " +
//                " JOIN " +
//                " (SELECT f_followed_user_id FROM FOLLOWS WHERE f_user_id = ?) followedIds " +
//                " on f_followed_user_id = users.u_id " +
//                " order by u_id desc " + offsetAndLimitQuery + " ) ";
//
//        final String query =
//            " SELECT * FROM " +
//                " (SELECT * FROM USERS " + USER_WHERE_IDS_QUERY + " ) u" +
//                " JOIN " +
//                " ROLES on u.u_id = roles.r_user_id ";
//
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.
//            query(query, USER_RS_EXTRACTOR, variables.toArray());
//    }
//
//
//    @Override
//    public Collection<Integer> getAllUserFollowingsIds(Long userId) {
//        final String query = "SELECT u_id FROM (SELECT * FROM USERS WHERE u_id IN " +
//            "(SELECT f_followed_user_id FROM FOLLOWS WHERE f_user_id = ?)) u ";
//
//
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.
//            query(query, (rs, rowNum) -> rs.getInt("u_id"), userId);
//    }
//
//    @Override
//    public Collection<Integer> getAllUserFollowersIds(Long userId) {
//        final String query = "SELECT u_id FROM (SELECT * FROM USERS WHERE u_id IN " +
//            "(SELECT f_user_id FROM FOLLOWS WHERE f_followed_user_id = ?)) u ";
//
//        LOGGER.debug("Executing query: {}", query);
//
//        return jdbcTemplate.
//            query(query, (rs, rowNum) -> rs.getInt("u_id"), userId);
//    }
//
//    @Override
//    public Integer getUserFollowersCount(Long userId) {
//        final String query = "SELECT count(f_user_id) total FROM FOLLOWS WHERE f_followed_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query,
//            (rs, rowNum) -> rs.getInt("total"),
//            new Object[]{userId}).stream().findFirst().orElse(0);
//    }
//
//    @Override
//    public Integer getUserFollowingCount(Long userId) {
//        final String query = "SELECT count(f_followed_user_id) total FROM FOLLOWS WHERE f_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query,
//            (rs, rowNum) -> rs.getInt("total"),
//            new Object[]{userId}).stream().findFirst().orElse(0);
//    }

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