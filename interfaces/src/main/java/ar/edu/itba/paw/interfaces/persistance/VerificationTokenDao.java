package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getVerificationToken(long id);

    VerificationToken createVerificationToken(long userId, String token, LocalDateTime expirationDate);

    Optional<VerificationToken> getTokenByValue(String token);

    void removeTokenById(long id);
    void removeTokenByUserId(long userId);

    Optional<VerificationToken> getTokenByUserId(long userId);

}
