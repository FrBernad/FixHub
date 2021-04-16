package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PasswordResetTokenDao;
import ar.edu.itba.paw.models.PasswordResetToken;
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
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    @Autowired
    private DataSource ds;

    private static final RowMapper<PasswordResetToken> PASSWORD_RESET_TOKEN_ROW_MAPPER = (rs, rowNum) ->
        new PasswordResetToken(rs.getLong("prt_id"),
            rs.getString("prt_token"),
            rs.getLong("prt_user_id"),
            rs.getTimestamp("prt_expiration_date").toLocalDateTime());

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PasswordResetTokenDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("password_reset_tokens").usingGeneratedKeyColumns("prt_id");
    }


    @Override
    public Optional<PasswordResetToken> getToken(long id) {
        return jdbcTemplate.query("SELECT * FROM password_reset_tokens WHERE prt_id = ?",
            new Object[]{id},
            PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PasswordResetToken createToken(long userId, String token, LocalDateTime expirationDate) {
        Map<String, Object> values = new HashMap<>();
        values.put("prt_user_id", userId);
        values.put("prt_token", token);
        values.put("prt_expiration_date", expirationDate);
        Number tokenId = simpleJdbcInsert.executeAndReturnKey(values);

        return new PasswordResetToken(tokenId.longValue(), token, userId, expirationDate);//never returns null
    }

    @Override
    public Optional<PasswordResetToken> getTokenByValue(String token) {
        return jdbcTemplate.query("SELECT * FROM password_reset_tokens WHERE prt_token = ?",
            new Object[]{token},
            PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void removeTokenById(long id) {
        jdbcTemplate.update("DELETE from password_reset_tokens where prt_id = ?", id);
    }

    @Override
    public void removeTokenByUserId(long userId) {
        jdbcTemplate.update("DELETE from password_reset_tokens where prt_user_id = ?", userId);
    }

    @Override
    public Optional<PasswordResetToken> getTokenByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM password_reset_tokens WHERE prt_user_id = ?",
            new Object[]{userId},
            PASSWORD_RESET_TOKEN_ROW_MAPPER).stream().findFirst();
    }
}
