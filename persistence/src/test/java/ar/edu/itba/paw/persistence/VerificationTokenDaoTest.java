//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
//import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
//import ar.edu.itba.paw.models.token.VerificationToken;
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
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql(scripts = "classpath:verification-token-dao-test.sql")
//@Transactional
//public class VerificationTokenDaoTest {
//
//    private static final long ID = 1;
//
//    @Autowired
//    private VerificationTokenDao verificationTokenDao;
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Test
//    public void testRemoveTokenById() {
//        verificationTokenDao.removeTokenById(ID);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verification_tokens", "vt_id = " + ID));
//    }
//
//    @Test
//    public void testGetTokenByUserId() {
//        VerificationToken verificationToken = verificationTokenDao.getTokenByUserId(ID).orElseThrow(UserNotFoundException::new);
//        assertEquals(ID, verificationToken.getUserId());
//    }
//
//    @Test
//    public void testRemoveTokenByUserId() {
//        verificationTokenDao.removeTokenByUserId(ID);
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verification_tokens", "vt_user_id = " + ID));
//    }
//
//    @Test
//    public void testGetVerificationToken() {
//        VerificationToken verificationToken = verificationTokenDao.getVerificationToken(ID).orElseThrow(UserNotFoundException::new);
//        assertEquals(ID, verificationToken.getId());
//    }
//
//
//
//}
