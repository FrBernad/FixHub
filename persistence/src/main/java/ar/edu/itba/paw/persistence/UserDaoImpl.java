package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Roles;
import ar.edu.itba.paw.models.UserInfo;
import ar.edu.itba.paw.models.UserStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userSimpleJdbcInsert;
    private SimpleJdbcInsert roleSimpleJdbcInsert;

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
                    new ArrayList<>()));
            }

            userMap.get(userId).addRole(Roles.valueOf(rs.getString("r_role")));
        }

        return userMap.values();
    };

    private final Collection<Roles> roles = Arrays.asList(Roles.values().clone());

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("u_id");
        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("ROLES").usingGeneratedKeyColumns("r_id");
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
    public void updateUserInfo(UserInfo userInfo, long userId) {
        jdbcTemplate.update("UPDATE users SET u_name = ?, u_surname = ?, " +
                " u_city = ?, u_phone_number = ?," +
                " u_state = ? where u_id = ?",
            userInfo.getName(), userInfo.getSurname(),
            userInfo.getCity(), userInfo.getPhoneNumber(), userInfo.getState(), userId);
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
                " FROM ((SELECT * FROM jobs LEFT JOIN reviews ON j_id = r_job_id) aux1 JOIN " +
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

        return new User(id.longValue(), password, name, surname, email, phoneNumber, state, city, roles);
    }

}