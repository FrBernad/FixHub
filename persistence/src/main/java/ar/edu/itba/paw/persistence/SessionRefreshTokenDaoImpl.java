package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.SessionRefreshTokenDao;
import ar.edu.itba.paw.models.token.SessionRefreshToken;
import ar.edu.itba.paw.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class SessionRefreshTokenDaoImpl implements SessionRefreshTokenDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public SessionRefreshToken createToken(User user, String token, LocalDateTime expirationDate) {
        final SessionRefreshToken sessionRefreshToken = new SessionRefreshToken(token, user, expirationDate);

        em.persist(sessionRefreshToken);

        return sessionRefreshToken;
    }

    @Override
    public Optional<SessionRefreshToken> getTokenByValue(String token) {
        return em.
            createQuery("from SessionRefreshToken where value = :token",
                SessionRefreshToken.class)
            .setParameter("token", token)
            .getResultList()
            .stream()
            .findFirst();
    }

    @Override
    public void removeToken(SessionRefreshToken token) {
        em.remove(token);
    }

    @Override
    public Optional<SessionRefreshToken> getTokenByUser(User user) {

        return em.createQuery(
            "FROM SessionRefreshToken srt where srt.user.id = :userId", SessionRefreshToken.class)
            .setParameter("userId", user.getId())
            .getResultList()
            .stream()
            .findFirst();

    }

}
