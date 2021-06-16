package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.models.token.PasswordResetToken;
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
@Transactional
@Sql(scripts = "classpath:password-reset-token-dao-test.sql")
public class PasswordResetTokenDaoTest {

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

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

        JdbcTestUtils.deleteFromTables(jdbcTemplate,"password_reset_tokens");

        User user = em.find(User.class, USER.getId());

        PasswordResetToken prt = passwordResetTokenDao.createToken(user, PRT_TOKEN_VALUE, EXPIRATION_DATE);

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
            "password_reset_tokens", "prt_id = " + PRT_TOKEN_ID + "and prt_user_id = " + USER.getId()));
        assertEquals(PRT_TOKEN_VALUE, prt.getValue());
        assertEquals(user, prt.getUser());
    }

    @Test
    public void testGetTokenByUser() {
        final Optional<PasswordResetToken> prt = passwordResetTokenDao.getTokenByUser(USER);
        assertTrue(prt.isPresent());
    }

    @Test
    public void testGetTokenByValidValue() {
        final Optional<PasswordResetToken> prt = passwordResetTokenDao.getTokenByValue(PRT_TOKEN_VALUE);
        assertTrue(prt.isPresent());
    }


    @Test
    public void testGetTokenByInvalidValue() {
        final Optional<PasswordResetToken> prt = passwordResetTokenDao.getTokenByValue("invalid");
        assertFalse(prt.isPresent());
    }

    @Test
    public void testRemoveToken() {
        final PasswordResetToken prt = em.find(PasswordResetToken.class, PRT_TOKEN_ID);

        passwordResetTokenDao.removeToken(prt);

        em.flush();

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "password_reset_tokens", "prt_id = " + PRT_TOKEN_ID));
    }

}
