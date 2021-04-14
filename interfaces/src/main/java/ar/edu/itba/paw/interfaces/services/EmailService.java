package ar.edu.itba.paw.interfaces.services;


import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {
    void sendMail(final String template, final String subject, final Map<String, Object> variables) throws MessagingException;
}
