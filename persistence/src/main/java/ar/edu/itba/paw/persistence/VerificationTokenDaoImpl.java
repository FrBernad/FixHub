package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.VerificationTokenDao;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenDaoImpl.class);

    @Override
    public Optional<VerificationToken> getVerificationToken(long id) {
        LOGGER.debug("Retrieving verification token with id {}", id);
        return Optional.ofNullable(em.find(VerificationToken.class, id));
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token, LocalDateTime expirationDate) {
        LOGGER.debug("Creating verification token for user with id {}", user.getId());
        final VerificationToken verificationToken = new VerificationToken(token, user, expirationDate);

        em.persist(verificationToken);

        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> getTokenByValue(String token) {
        LOGGER.debug("Retrieving token with value {}", token);
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
        LOGGER.debug("Removing token");
        em.remove(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getTokenByUser(User user) {
        LOGGER.debug("Retrieving token by user");
        return em.createQuery(
            "FROM VerificationToken vt where vt.user.id = :userId", VerificationToken.class)
            .setParameter("userId", user.getId())
            .getResultList()
            .stream()
            .findFirst();
    }

}
