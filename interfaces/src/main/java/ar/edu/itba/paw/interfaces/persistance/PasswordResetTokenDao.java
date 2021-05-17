package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {

    Optional<PasswordResetToken> getToken(long id);

    PasswordResetToken createToken(User user, String token, LocalDateTime expirationDate);

    Optional<PasswordResetToken> getTokenByValue(String token);

    void removeToken(PasswordResetToken token);

    Optional<PasswordResetToken> getTokenByUser(User user);
}
