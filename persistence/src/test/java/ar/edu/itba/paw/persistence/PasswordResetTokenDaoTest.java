//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//
//@Rollback
//@Sql(scripts = "classpath:review-test.sql")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class PasswordResetTokenDaoTest {
//
//    private static final long ID = 1L;
//    private static final String TOKEN = UUID.randomUUID().toString();
//    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now().plusHours(1);
//
//    @Autowired
//    private PasswordResetTokenDao passwordResetTokenDao;
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    private SimpleJdbcInsert jdbcInsert;
//
//    @Before
//    public void setUp() {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("password_reset_tokens").usingGeneratedKeyColumns("prt_id");
//    }
//
//    @Test
//    public void testRemoveTokenByUserId() {
//
//        Map<String, Object> values = new HashMap<>();
//        values.put("prt_user_id", ID);
//        values.put("prt_token", TOKEN);
//        values.put("prt_expiration_date", Timestamp.valueOf(EXPIRATION_DATE));
//
//        jdbcInsert.execute(values);
//
//        passwordResetTokenDao.removeTokenByUserId(ID);
//
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "password_reset_tokens", "prt_id = " + ID));
//
//    }
//
//}
