//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.exceptions.DuplicateUserException;
//import ar.edu.itba.paw.interfaces.persistance.UserDao;
//import ar.edu.itba.paw.models.Roles;
//import ar.edu.itba.paw.models.User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@Rollback //Se vuelven atras las transacciones luego de cada test para que la base de datos vuelva a su estado original
//@Sql(scripts = "classpath:user-test.sql")
////sirve para partir siempre de una base de datos conocida. Se ejecuta antes de cada test
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class UserJdbcDaoTest {
//
//    private static final String PASSWORD = "password";
//    private static final String NAME = "name1";
//    private static final String SURNAME = "surname1";
//    private static final String EMAIL = "email1";
//    private static final String PHONENUMBER = "phoneNumber1";
//    private static final String STATE = "state1";
//    private static final String CITY = "city1";
//
//    @Autowired
//    private DataSource ds;
//    @Autowired
//    private UserDao userDao;
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Test
//    public void testCreate() throws DuplicateUserException {
//        final Collection<Roles> DEFAULT_ROLES = Collections.unmodifiableCollection(Arrays.asList(Roles.USER, Roles.NOT_VERIFIED));
//
//        final User user = userDao.createUser(PASSWORD, NAME, SURNAME, EMAIL, PHONENUMBER, STATE, CITY, new ArrayList<>());
//
//        Assert.assertNotNull(user);
//        Assert.assertEquals(PASSWORD, user.getPassword());
//        Assert.assertEquals(NAME, user.getName());
//        Assert.assertEquals(SURNAME, user.getSurname());
//        Assert.assertEquals(EMAIL, user.getEmail());
//        Assert.assertEquals(PHONENUMBER, user.getPhoneNumber());
//        Assert.assertEquals(CITY, user.getCity());
//        Assert.assertEquals(STATE, user.getState());
//
//        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
//    }
//}
//
