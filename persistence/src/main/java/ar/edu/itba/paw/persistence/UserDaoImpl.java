package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
import ar.edu.itba.paw.interfaces.persistance.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRoles;
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

    private static final ResultSetExtractor<Collection<User>> USER_ROW_MAPPER = rs -> {
        Map<Long, User> userMap = new HashMap<>();

        long userId;

        while (rs.next()) {

            userId = rs.getLong("id");

            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId,
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("state"),
                    rs.getString("city"),
                    new ArrayList<>()));
            }

            userMap.get(userId).addRole(UserRoles.valueOf(rs.getString("role")));
        }

        return userMap.values();
    };

    private final Collection<UserRoles> userRoles = Arrays.asList(UserRoles.values().clone());

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        userSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("id");
        roleSimpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("ROLES").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getUserById(long id) {

        return jdbcTemplate.
            query("SELECT * FROM USERS u JOIN ROLES r on u.id=r.user_id WHERE u.id = ?", new Object[]{id}, USER_ROW_MAPPER)
            .stream()
            .findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM (SELECT * FROM USERS WHERE EMAIL = ?) as AUX JOIN roles r on AUX.id = r.user_id", new Object[]{email}, USER_ROW_MAPPER)
            .stream()
            .findFirst();
    }

    @Override
    public Collection<UserRoles> getUserRoles() {
        return userRoles;
    }

    @Override
    public User createUser(String password, String name, String surname, String email, String phoneNumber, String state, String city) throws DuplicateUserException {
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("name", name);
        map.put("surname", surname);
        map.put("email", email);
        map.put("phone_number", phoneNumber);
        map.put("state", state);
        map.put("city", city);

        final Number id;

        try {
            id = userSimpleJdbcInsert.executeAndReturnKey(map);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new DuplicateUserException();
        }

        Collection<UserRoles> roles = new ArrayList<>();
        roles.add(UserRoles.ROLE_USER);

        return new User(id, password, name, surname, email, phoneNumber, state, city, roles);
    }

}