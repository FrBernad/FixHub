package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.token.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {
    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        return Optional.empty();
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate) {
        return null;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        return Optional.empty();
    }

    @Override
    public void removeTokenById(long id) {

    }

    @Override
    public void removeTokenByUserId(long userId) {

    }

    @Override
    public Optional<VerificationToken> getTokenByUserId(long userId) {
        return Optional.empty();
    }
//
//    private static final RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER = (rs, rowNum) ->
//        new VerificationToken(rs.getLong("vt_id"),
//            rs.getString("vt_token"),
//            rs.getLong("vt_user_id"),
//            rs.getTimestamp("vt_expiration_date").toLocalDateTime());
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert simpleJdbcInsert;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);
//
//
//    @Autowired
//    public VerificationTokenDaoImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("VERIFICATION_TOKENS").usingGeneratedKeyColumns("vt_id");
//    }
//
//
//    @Override
//    public Optional<VerificationToken> getVerificationToken(long id) {
//        final String query = "SELECT * FROM VERIFICATION_TOKENS WHERE vt_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{id}, VERIFICATION_TOKEN_ROW_MAPPER)
//            .stream().findFirst();
//    }
//
//    @Override
//    public VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate) {
//        final Map<String, Object> values = new HashMap<>();
//        values.put("vt_user_id", userId);
//        values.put("vt_token", token);
//        values.put("vt_expiration_date", expirationDate);
//        final Number tokenId = simpleJdbcInsert.executeAndReturnKey(values);
//        LOGGER.info("Created new verification token for user with id {}", userId);
//
//        return new VerificationToken(tokenId.longValue(), token, userId, expirationDate);//never returns null
//    }
//
//    @Override
//    public Optional<VerificationToken> getTokenByValue(String token) {
//        final String query = "SELECT * FROM verification_tokens WHERE vt_token=?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{token}, VERIFICATION_TOKEN_ROW_MAPPER)
//            .stream().findFirst();
//    }
//
//    @Override
//    public void removeTokenById(long id) {
//        final String query = "DELETE from verification_tokens where vt_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        jdbcTemplate.update(query, id);
//    }
//
//    @Override
//    public void removeTokenByUserId(long userId) {
//        final String query = "DELETE from verification_tokens where vt_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        jdbcTemplate.update(query, userId);
//    }
//
//    @Override
//    public Optional<VerificationToken> getTokenByUserId(long userId) {
//        final String query = "SELECT * FROM verification_tokens WHERE vt_user_id=?";
//        LOGGER.debug("Executing query: {}", query);
//        return jdbcTemplate.query(query, new Object[]{userId}, VERIFICATION_TOKEN_ROW_MAPPER)
//            .stream().findFirst();
//    }
}
