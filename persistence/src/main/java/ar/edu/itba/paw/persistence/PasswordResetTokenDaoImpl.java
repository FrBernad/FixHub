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

    //FIXME:
    @Override
    public Optional<PasswordResetToken> getToken(long id) {
        return Optional.ofNullable(em.find(PasswordResetToken.class, id));
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

    //FIXME:
//    @Override
//    public void removeTokenByUserId(long userId) {
//        final String query = "DELETE from password_reset_tokens where prt_user_id = ?";
//        LOGGER.debug("Executing query: {}", query);
//        jdbcTemplate.update(query, userId);
//    }
//
}
