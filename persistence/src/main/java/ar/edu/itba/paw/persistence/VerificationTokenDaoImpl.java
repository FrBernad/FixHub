package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @Autowired
    private DataSource ds;

    private static final RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER = (rs, rowNum) ->
        new VerificationToken(rs.getString("vt_token"),
            rs.getLong("vt_id"),
            null
        );

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public VerificationTokenDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("VERIFICATION_TOKENS").usingGeneratedKeyColumns("vt_id");
    }


    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        return jdbcTemplate.query("SELECT * FROM VERIFICATION_TOKENS WHERE vt_id = ?",
            new Object[]{id},
            VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public VerificationToken createVerificationToken(long userId, String token) {
        Map<String, Object> values = new HashMap<>();
        values.put("vt_user_id", userId);
        values.put("vt_token", token);
        Number tokenId = simpleJdbcInsert.executeAndReturnKey(values);

        return getVerificationToken(tokenId.longValue()).get();
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        return Optional.empty();
    }
}
