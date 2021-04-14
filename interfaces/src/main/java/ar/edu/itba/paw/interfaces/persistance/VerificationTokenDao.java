package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao {

    Optional<VerificationToken> getVerificationToken(long id);

    VerificationToken createVerificationToken(long userId, String token);

    Optional<User> getUserByToken(String token);

}
