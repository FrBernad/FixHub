package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userSimpleJdbcInsert;
    private SimpleJdbcInsert roleSimpleJdbcInsert;

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
        return null;
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