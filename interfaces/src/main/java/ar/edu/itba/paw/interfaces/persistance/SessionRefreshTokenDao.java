package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.token.SessionRefreshToken;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRefreshTokenDao {

    SessionRefreshToken createToken(User user, String token, LocalDateTime expirationDate);

    Optional<SessionRefreshToken> getTokenByValue(String token);

    void removeToken(SessionRefreshToken token);

    Optional<SessionRefreshToken> getTokenByUser(User user);
}
