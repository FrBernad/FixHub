package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "USERS(" +
                "id SERIAL," +
                "name TEXT," +
                "password TEXT," +
                "PRIMARY KEY(id))");
    }

    public Optional<User> get(String id) {
        return null;
    }

    public List<User> list() {
        return null;
}

    @Override
    public User createUser(String name, String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("password",password);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new User(id,name,password);
    }

}