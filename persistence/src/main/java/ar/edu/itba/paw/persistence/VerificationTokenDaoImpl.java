package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.token.VerificationToken;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);

    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        return Optional.empty();
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token, LocalDateTime expirationDate) {
        final VerificationToken verificationToken = new VerificationToken(token, user, expirationDate);

        em.persist(verificationToken);

        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        return em.
            createQuery("from VerificationToken where value = :token",
                VerificationToken.class)
            .setParameter("token", token)
            .getResultList()
            .stream()
            .findFirst();
    }


    @Override
    public void removeToken(VerificationToken verificationToken) {
        em.remove(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getTokenByUser(User user) {
        return em.createQuery(
            "FROM VerificationToken vt where vt.user.id = :userId", VerificationToken.class)
            .setParameter("userId", user.getId())
            .getResultList()
            .stream()
            .findFirst();
    }

}
