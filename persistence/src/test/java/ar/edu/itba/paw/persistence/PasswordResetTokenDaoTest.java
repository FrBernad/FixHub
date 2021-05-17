//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.sql.DataSource;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Transactional
//@Sql(scripts = "classpath:password-reset-token-dao-test.sql")
//public class PasswordResetTokenDaoTest {
//
//    private static final long ID = 1L;
//
//    @Autowired
//    private PasswordResetTokenDao passwordResetTokenDao;
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//
//    @Before
//    public void setUp() {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Test
//    public void testRemoveTokenByUserId() {
//
//        passwordResetTokenDao.removeTokenByUserId(ID);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "password_reset_tokens", "prt_id = " + ID));
//
//    }
//
//}
