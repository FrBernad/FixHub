package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.UserStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userSimpleJdbcInsert;
    private SimpleJdbcInsert roleSimpleJdbcInsert;
    private SimpleJdbcInsert contactInfoSimpleJdbcInsert;
    private SimpleJdbcInsert contactProviderSimpleJdbcInsert;
    private SimpleJdbcInsert userScheduleSimpleJdbcInsert;
    private SimpleJdbcInsert userLocationSimpleJdbcInsert;


    private static final ResultSetExtractor<Collection<UserStats>> USER_STATS_ROW_MAPPER = rs -> {
        Map<Long, UserStats> statsMap = new HashMap<>();

        long userId;

        while (rs.next()) {

            userId = rs.getLong("u_id");

            if (!statsMap.containsKey(userId)) {
                statsMap.put(userId, new UserStats(
                    rs.getLong("totalJobs"),
                    rs.getLong("avgRating"),
                    rs.getLong("totalReviews")));
            }
        }

        return statsMap.values();
    };

    private static final ResultSetExtractor<Collection<User>> USER_ROW_MAPPER = rs -> {
        Map<Long, User> userMap = new HashMap<>();

        long userId;

        while (rs.next()) {

            userId = rs.getLong("u_id");

            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId,
                    rs.getString("u_password"),
                    rs.getString("u_name"),
                    rs.getString("u_surname"),
                    rs.getString("u_email"),
                    rs.getString("u_phone_number"),
                    rs.getString("u_state"),
                    rs.getString("u_city"),
                    new ArrayList<>(),
                    rs.getLong("u_profile_picture")));
            }

            userMap.get(userId).addRole(Roles.valueOf(rs.getString("r_role")));
        }

        return userMap.values();
    };

    private ResultSetExtractor<Collection<ContactInfo>> CONTACT_INFO_ROW_MAPPER = rs -> {
        List<ContactInfo> contactInfo = new LinkedList<>();
        while (rs.next()) {
            contactInfo.add(new ContactInfo(
                rs.getLong("ci_id"),
                rs.getLong("ci_user_id"),
                rs.getString("ci_state"),
                rs.getString("ci_city"),
                rs.getString("ci_street"),
                rs.getString("ci_address_number"),
                rs.getString("ci_floor"),
                rs.getString("ci_department_number")));
        }
        return contactInfo;
    };

    private static final ResultSetExtractor<Collection<JobContact>> CLIENT_ROW_MAPPER = rs -> {
        List<JobContact> contacts = new LinkedList<>();
        while (rs.next()) {
            ContactUser client = new ContactUser(rs.getLong("c_user_id"), rs.getString("u_name"), rs.getString("u_surname"), rs.getString("u_phone_number"), rs.getString("u_email"));
            ContactInfo contactInfo = new ContactInfo(rs.getLong("ci_id"), rs.getLong("ci_user_id"), rs.getString("ci_state"), rs.getString("ci_city"), rs.getString("ci_street"), rs.getString("ci_address_number"), rs.getString("ci_floor"), rs.getString("ci_department_number"));
            contacts.add(new JobContact(contactInfo, client, rs.getString("c_message"), rs.getTimestamp("c_date").toLocalDateTime(), rs.getLong("j_id"), rs.getString("j_job_provided"), JobCategory.valueOf(rs.getString("j_category"))));
        }
        return contacts;
    };

    private static final ResultSetExtractor<Collection<JobContact>> PROVIDER_ROW_MAPPER = rs -> {
        List<JobContact> contacts = new LinkedList<>();
        while (rs.next()) {
            ContactUser provider = new ContactUser(rs.getLong("c_provider_id"), rs.getString("u_name"), rs.getString("u_surname"), rs.getString("u_phone_number"), rs.getString("u_email"));
            ContactInfo contactInfo = new ContactInfo(rs.getLong("ci_id"), rs.getLong("ci_user_id"), rs.getString("ci_state"), rs.getString("ci_city"), rs.getString("ci_street"), rs.getString("ci_address_number"), rs.getString("ci_floor"), rs.getString("ci_department_number"));
            contacts.add(new JobContact(contactInfo, provider, rs.getString("c_message"), rs.getTimestamp("c_date").toLocalDateTime(), rs.getLong("j_id"), rs.getString("j_job_provided"), JobCategory.valueOf(rs.getString("j_category"))));
        }
        return contacts;
    };

    private final Collection<Roles> roles = Arrays.asList(Roles.values().clone());

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("u_id");
        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("ROLES").usingGeneratedKeyColumns("r_id");
        contactInfoSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("CONTACT_INFO").usingGeneratedKeyColumns("ci_id");
        contactProviderSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("CONTACT").usingGeneratedKeyColumns("c_id");
        userScheduleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USER_SCHEDULE");
        userLocationSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USER_LOCATION");
    }

    @Override
    public Optional<User> getUserById(long id) {

        return jdbcTemplate.
            query("SELECT * FROM USERS u JOIN ROLES r on u_id=r_user_id WHERE u_id = ?", new Object[]{id}, USER_ROW_MAPPER)
            .stream()
            .findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM USERS WHERE u_email = ?) as AUX JOIN roles r on u_id = r_user_id", new Object[]{email}, USER_ROW_MAPPER)
            .stream()
            .findFirst();
    }

    @Override
    public Collection<Roles> getUserRoles() {
        return roles;
    }

    @Override
    public Optional<User> updateRoles(long userId, Roles oldVal, Roles newVal) {
        if (jdbcTemplate.update("UPDATE roles set r_role = ? where r_user_id = ? and r_role = ?",
            newVal.name(), userId, oldVal.name()) == 1) {
            return getUserById(userId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updatePassword(long userId, String password) {
        if (jdbcTemplate.update("UPDATE USERS set u_password = ? where u_id = ?",
            password, userId) == 1) {
            return getUserById(userId);
        }
        return Optional.empty();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, User user) {
        jdbcTemplate.update("UPDATE users SET u_name = ?, " +
                "u_surname = ?, u_city = ?, u_phone_number = ?,u_state = ? " +
                " where u_id = ?", userInfo.getName(), userInfo.getSurname(),
            userInfo.getCity(), userInfo.getPhoneNumber(), userInfo.getState(), user.getId());
    }

    @Override
    public void addRole(long userId, Roles newRole) {
        Map<String, Object> userRoles = new HashMap<>();
        userRoles.put("r_user_id", userId);

        userRoles.put("r_role", newRole.name());
        roleSimpleJdbcInsert.execute(userRoles);
    }

    @Override
    public Optional<UserStats> getUserStatsById(long id) {
        return jdbcTemplate.query(
            "SELECT u_id, count(j_id) AS totalJobs,count(r_rating) AS totalReviews,avg(coalesce(r_rating,0)) AS avgRating" +
                " FROM ((SELECT * FROM jobs LEFT OUTER JOIN reviews ON j_id = r_job_id) aux1 RIGHT OUTER JOIN " +
                " (SELECT * FROM users WHERE u_id = ? ) aux2 ON u_id=j_provider_id) GROUP BY u_id",
            USER_STATS_ROW_MAPPER,
            new Object[]{id}).stream().findFirst();
    }


    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city, Collection<Roles> roles) throws DuplicateUserException {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("u_password", password);
        userInfo.put("u_name", name);
        userInfo.put("u_surname", surname);
        userInfo.put("u_email", email);
        userInfo.put("u_phone_number", phoneNumber);
        userInfo.put("u_state", state);
        userInfo.put("u_city", city);
        userInfo.put("u_profile_picture", null);

        final Number id;

        try {
            id = userSimpleJdbcInsert.executeAndReturnKey(userInfo);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        Map<String, Object> userRoles = new HashMap<>();
        userRoles.put("r_user_id", id);

        for (Roles role : roles) {
            userRoles.put("r_role", role.name());
            roleSimpleJdbcInsert.execute(userRoles);
        }

        return new User(id.longValue(), password, name, surname, email, phoneNumber, state, city, roles, null);
    }

    public Collection<ContactInfo> getContactInfo(User user) {
        return jdbcTemplate.query("SELECT * FROM CONTACT_INFO WHERE ci_user_id = ? ", new Object[]{user.getId()}, CONTACT_INFO_ROW_MAPPER);
    }

    public ContactInfo addContactInfo(User user, String state, String city, String street, String addressNumber, String floor, String departmentNumber) {
        final Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("ci_user_id", user.getId());
        contactInfo.put("ci_city", city);
        contactInfo.put("ci_state", state);
        contactInfo.put("ci_street", street);
        contactInfo.put("ci_floor", floor);
        contactInfo.put("ci_address_number", addressNumber);
        contactInfo.put("ci_department_number", departmentNumber);
        final Number id = contactInfoSimpleJdbcInsert.executeAndReturnKey(contactInfo);
        return new ContactInfo(id.longValue(), user.getId(), state, city, street, addressNumber, floor, departmentNumber);
    }

    public Optional<ContactInfo> getContactInfoById(Long contactInfoId) {
        return jdbcTemplate.query("SELECT * FROM CONTACT_INFO WHERE ci_id = ? ", new Object[]{contactInfoId}, CONTACT_INFO_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void addClient(Long providerId, Long jobId, User user, Long contactInfoId, String message, Timestamp time) {
        final Map<String, Object> contactMap = new HashMap<>();
        contactMap.put("c_provider_id", providerId);
        contactMap.put("c_job_id", jobId);
        contactMap.put("c_user_id", user.getId());
        contactMap.put("c_info_id", contactInfoId);
        contactMap.put("c_message", message);
        contactMap.put("c_date", time.toLocalDateTime());
        contactProviderSimpleJdbcInsert.execute(contactMap);
    }

    @Override
    public Collection<JobContact> getClientsByProviderId(Long providerId, int page, int itemsPerPage) {
        List<Object> variables = new LinkedList<>();

        variables.add(providerId);

        String offset = " ";
        if (page > 0) {
            offset = " OFFSET ? ";
            variables.add(page * itemsPerPage);
        }
        String limit = " ";
        if (itemsPerPage > 0) {
            limit = " LIMIT ? ";
            variables.add(itemsPerPage);
        }

        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM " +
                "(SELECT * FROM CONTACT JOIN CONTACT_INFO on c_info_id = ci_id where c_provider_id = ?)" +
                " AUX JOIN USERS on u_id = c_user_id) AUX2 JOIN JOBS on j_id = c_job_id ORDER BY c_date DESC " + offset + limit,
            variables.toArray(), CLIENT_ROW_MAPPER);
    }

    @Override
    public int getClientsCountByProviderId(Long providerId) {
        return jdbcTemplate.query("SELECT count(c_provider_id) as total FROM contact WHERE  c_provider_id = ?",
            new Object[]{providerId},
            (rs, num) -> rs.getInt("total")).stream().findFirst().orElse(0);
    }

    @Override
    public Collection<JobContact> getProvidersByClientId(Long clientId, int page, int itemsPerPage) {
        List<Object> variables = new LinkedList<>();

        variables.add(clientId);

        String offset = " ";
        if (page > 0) {
            offset = " OFFSET ? ";
            variables.add(page * itemsPerPage);
        }
        String limit = " ";
        if (itemsPerPage > 0) {
            limit = " LIMIT ? ";
            variables.add(itemsPerPage);
        }
        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM " +
            "(SELECT * FROM CONTACT JOIN CONTACT_INFO on c_info_id = ci_id where c_user_id = ? ) AUX " +
            "JOIN USERS on u_id = c_provider_id) AUX2 JOIN JOBS on j_id = c_job_id ORDER BY c_date DESC" + offset + limit, variables.toArray(), PROVIDER_ROW_MAPPER);

    }

    @Override
    public int getProvidersCountByClientId(Long clientId) {
        return jdbcTemplate.query("SELECT count(c_user_id) as total FROM contact WHERE  c_user_id = ?",
            new Object[]{clientId},
            (rs, num) -> rs.getInt("total")).stream().findFirst().orElse(0);
    }

    @Override
    public void addSchedule(Long userId, String startTime, String endTime) {
        Map<String, Object> schedule = new HashMap<>();

        schedule.put("us_user_id", userId);
        schedule.put("us_start_time", startTime);
        schedule.put("us_end_time", endTime);
        userScheduleSimpleJdbcInsert.execute(schedule);
    }


    @Override
    public void addLocation(Long userId, List<Long> citiesId) {
        Map<String, Object> location = new HashMap<>();
        for (Long cityId : citiesId) {
            location.put("ul_user_id", userId);
            location.put("ul_city_id", cityId);
            userLocationSimpleJdbcInsert.execute(location);
        }
    }

    @Override
    public ProviderLocation getLocationByProviderId(Long providerId) {
        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM USER_LOCATION JOIN CITIES on ul_city_id = c_id where ul_user_id = ?) AUX JOIN states on c_state_id = s_id" +
            " ORDER BY c_name ", new Object[]{providerId}, (rs) -> {
            ProviderLocation location = null;
            while (rs.next()) {
                if(location == null ){
                     location = new ProviderLocation(rs.getLong("ul_user_id"),new LinkedList<>(),rs.getString("s_name"));
                }
                location.getCities().add(new City(rs.getLong("ul_city_id"),rs.getString("c_name")));
            }
           return location;
        });
    }


}