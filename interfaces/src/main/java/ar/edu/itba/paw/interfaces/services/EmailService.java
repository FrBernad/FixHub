package ar.edu.itba.paw.interfaces.services;


import ar.edu.itba.paw.models.contact.ContactDto;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;

import java.util.Locale;

public interface EmailService {

    void sendVerificationEmail(User user, VerificationToken token, Locale locale);

    void sendPasswordResetEmail(User user, PasswordResetToken token, Locale locale);

    void sendProviderNotificationEmail(User user, Locale locale);

    void sendJobRequestConfirmationEmail(ContactDto contactDto, Locale locale);

    void sendJobRequestEmail(ContactDto contactDto, Locale locale);

    void sendJobCancellationEmail(JobContact jobContact, Locale locale);

    void sendJobConfirmationEmail(JobContact jobContact, Locale locale);

    void sendJobFinishedEmail(JobContact jobContact, Locale locale);

}
