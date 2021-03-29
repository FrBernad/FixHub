package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
            new User(rs.getLong("id"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("state"),
                    rs.getString("city"));

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("USERS").usingGeneratedKeyColumns("id");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " +
                "USERS(" +
                "id SERIAL," +
                "password TEXT," +
                "name TEXT," +
                "surname TEXT, "+
                "email TEXT UNIQUE,"+
                "phoneNumber TEXT,"+
                "state TEXT,"+
                "city TEXT,"+
                "PRIMARY KEY(id))");
    }

    public Optional<User> getById(long id) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE ID = ?", new Object[]{id},
                USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE EMAIL= ?",new Object[]{email},USER_ROW_MAPPER).stream().findFirst();
    }


    public List<User> list() {
        return jdbcTemplate.query("SELECT * FROM USERS", USER_ROW_MAPPER);
    }

    @Override
    public User createUser(String password,String name, String surname,  String email, String phoneNumber,String state,  String city){
        Map<String,Object> map = new HashMap<>();
        map.put("password",password);
        map.put("name",name);
        map.put("surname",surname);
        map.put("email",email);
        map.put("phoneNumber",phoneNumber);
        map.put("state",state);
        map.put("city",city);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new User(id,password,name,surname,email,phoneNumber,state,city);
    }

    /*@Override

    public User createUser(String name, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        final Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return new User(id, name, password);
    }*/

}