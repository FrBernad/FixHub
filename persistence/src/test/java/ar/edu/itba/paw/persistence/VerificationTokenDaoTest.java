package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = "classpath:verification-token-dao-test.sql")
@Transactional
public class VerificationTokenDaoTest {

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    private static final Long PRT_TOKEN_ID = 1L;

    private static final String PRT_TOKEN_VALUE = "83feb0af-f467-4374-91fb-8f96db3f9a23";

    private static final User USER = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 5, 29, 12, 30);

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateToken() {

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "verification_tokens");

        User user = em.find(User.class, USER.getId());

        VerificationToken vt = verificationTokenDao.createVerificationToken(user, PRT_TOKEN_VALUE, EXPIRATION_DATE);

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
            "verification_tokens", "vt_id = " + PRT_TOKEN_ID + "and vt_user_id = " + USER.getId()));
        assertEquals(PRT_TOKEN_VALUE, vt.getValue());
        assertEquals(user, vt.getUser());
    }

    @Test
    public void testGetTokenByUser() {
        final Optional<VerificationToken> vt = verificationTokenDao.getTokenByUser(USER);
        assertTrue(vt.isPresent());
    }

    @Test
    public void testGetTokenByValidValue() {
        final Optional<VerificationToken> vt = verificationTokenDao.getTokenByValue(PRT_TOKEN_VALUE);
        assertTrue(vt.isPresent());
    }


    @Test
    public void testGetTokenByInvalidValue() {
        final Optional<VerificationToken> vt = verificationTokenDao.getTokenByValue("invalid");
        assertFalse(vt.isPresent());
    }

    @Test
    public void testRemoveToken() {
        final VerificationToken vt = em.find(VerificationToken.class, PRT_TOKEN_ID);

        verificationTokenDao.removeToken(vt);

        em.flush();

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "password_reset_tokens", "prt_id = " + PRT_TOKEN_ID));
    }


}
