package ar.edu.itba.paw.interfaces.services;



import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService {
    void sendSimpleMail(final String userName,
                        final String userSurname,
                        final String userAddress,
                        final String userPhoneNumber,
                        final String userMessage,
                        final String providerEmail,
                        final Locale locale) throws MessagingException;
}
