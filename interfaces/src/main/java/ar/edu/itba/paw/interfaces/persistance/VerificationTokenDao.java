package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getVerificationToken(long id);

    VerificationToken createVerificationToken(User user, String token, LocalDateTime expirationDate);

    Optional<VerificationToken> getTokenByValue(String token);

    void removeToken(VerificationToken verificationToken);

    void removeTokenByUserId(long userId);

    Optional<VerificationToken> getTokenByUserId(long userId);

}
