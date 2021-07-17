package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.SessionRefreshTokenDao;
import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.token.SessionRefreshToken;
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
@Sql(scripts = "classpath:session-refresh-token-dao-test.sql")
@Transactional
public class SessionRefreshTokenDaoTest {

    @Autowired
    private SessionRefreshTokenDao sessionRefreshTokenDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    private static final Long SRT_TOKEN_ID = 1L;

    private static final String SRT_TOKEN_VALUE = "83feb0af-f467-4374-91fb-8f96db3f9a23";

    private static final User USER = Mockito.when(Mockito.mock(User.class).getId()).thenReturn(1L).getMock();

    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 5, 29, 12, 30);

    @Before
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateToken() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "session_refresh_tokens");

        User user = em.find(User.class, USER.getId());

        SessionRefreshToken srt = sessionRefreshTokenDao.createToken(user, SRT_TOKEN_VALUE, EXPIRATION_DATE);

        em.flush();

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,
            "session_refresh_tokens", "srt_id = " + SRT_TOKEN_ID + "and srt_user_id = " + USER.getId()));
        assertEquals(SRT_TOKEN_VALUE, srt.getValue());
        assertEquals(user, srt.getUser());
    }

    @Test
    public void testGetTokenByUser() {
        final Optional<SessionRefreshToken> srt = sessionRefreshTokenDao.getTokenByUser(USER);
        assertTrue(srt.isPresent());
    }

    @Test
    public void testGetTokenByValidValue() {
        final Optional<SessionRefreshToken> srt = sessionRefreshTokenDao.getTokenByValue(SRT_TOKEN_VALUE);
        assertTrue(srt.isPresent());
    }


    @Test
    public void testGetTokenByInvalidValue() {
        final Optional<SessionRefreshToken> srt = sessionRefreshTokenDao.getTokenByValue("invalid");
        assertFalse(srt.isPresent());
    }

    @Test
    public void testRemoveToken() {
        final SessionRefreshToken srt = em.find(SessionRefreshToken.class, SRT_TOKEN_ID);

        sessionRefreshTokenDao.removeToken(srt);

        em.flush();

        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "session_refresh_tokens", "srt_id = " + SRT_TOKEN_ID));
    }


}
