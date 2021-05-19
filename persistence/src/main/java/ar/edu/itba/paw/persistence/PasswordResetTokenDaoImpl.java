package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<PasswordResetToken> getToken(long id) {
        return Optional.empty();
    }

    @Override
    public PasswordResetToken createToken(User user, String token, LocalDateTime expirationDate) {
        final PasswordResetToken resetToken = new PasswordResetToken(token, user, expirationDate);

        em.persist(resetToken);

        return resetToken;
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        return em.
            createQuery("from PasswordResetToken where value = :token",
                PasswordResetToken.class)
            .setParameter("token", token)
            .getResultList()
            .stream()
            .findFirst();
    }

    @Override
    public void removeToken(PasswordResetToken token) {
        em.remove(token);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByUser(User user) {

        return em.createQuery(
            "FROM PasswordResetToken prt where prt.user.id = :userId", PasswordResetToken.class)
            .setParameter("userId", user.getId())
            .getResultList()
            .stream()
            .findFirst();

    }

//    private static final RowMapper<PasswordResetToken> PASSWORD_RESET_TOKEN_ROW_MAPPER = (rs, rowNum) ->
//        new PasswordResetToken(rs.getLong("prt_id"),
//            rs.getString("prt_token"),
//            rs.getLong("prt_user_id"),
//            rs.getTimestamp("prt_expiration_date").toLocalDateTime());
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert simpleJdbcInsert;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenDaoImpl.class);
//
//    @Autowired
//    public PasswordResetTokenDaoImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("password_reset_tokens").usingGeneratedKeyColumns("prt_id");
//    }
//
//    @Override
//    public Optional<PasswordResetToken> getToken(long id) {
//        return jdbcTemplate.query("SELECT * FROM password_reset_tokens WHERE prt_id = ?",
//            new Object[]{id},
//            PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
//    }
//
//    @Override
//    public PasswordResetToken createToken(long userId, String token, LocalDateTime expirationDate) {
//        final Map<String, Object> values = new HashMap<>();
//        values.put("prt_user_id", userId);
//        values.put("prt_token", token);
//        values.put("prt_expiration_date", expirationDate);
//        final Number tokenId = simpleJdbcInsert.executeAndReturnKey(values);
//        LOGGER.debug("Created new reset token with id {}", tokenId);
//
//        return new PasswordResetToken(tokenId.longValue(), token, userId, expirationDate);//never returns null
//    }
//
//    @Override
//    public Optional<PasswordResetToken> getTokenByValue(String token) {
//        final String query = "SELECT * FROM password_reset_tokens WHERE prt_token = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{token}, PASSWORD_RESET_TOKEN_ROW_MAPPER)
//            .stream().findFirst();
//    }
//
//    @Override
//    public void removeTokenById(long id) {
//        final String query = "DELETE from password_reset_tokens where prt_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        jdbcTemplate.update(query, id);
//    }
//
//    @Override
//    public void removeTokenByUserId(long userId) {
//        final String query = "DELETE from password_reset_tokens where prt_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        jdbcTemplate.update(query, userId);
//    }
//
//    @Override
//    public Optional<PasswordResetToken> getTokenByUserId(long userId) {
//        final String query = "SELECT * FROM password_reset_tokens WHERE prt_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId}, PASSWORD_RESET_TOKEN_ROW_MAPPER)
//            .stream().findFirst();
//    }
}
