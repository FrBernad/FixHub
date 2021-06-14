package ar.edu.itba.paw.interfaces.services;


import ar.edu.itba.paw.models.contact.ContactDto;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;

import javax.mail.MessagingException;
import java.util.Locale;
import java.util.Map;

public interface EmailService {

    void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException;

    void sendVerificationEmail(User user, VerificationToken token);

    void sendPasswordResetEmail(User user, PasswordResetToken token);

    void sendProviderNotificationEmail(User user);

    void sendJobRequestConfirmationEmail(ContactDto contactDto);

    void sendJobRequestEmail(ContactDto contactDto);

    void sendJobCancellationEmail(ContactDto contactDto);

    void sendJobConfirmationEmail(ContactDto contactDto);

}
