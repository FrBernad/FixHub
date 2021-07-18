package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.contact.ContactInfo;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String FIXHUB_EMAIL = "fixhubcomp@gmail.com";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private URL appBaseUrl;

    private void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws
        MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariables(variables);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        final String to = (String) variables.get("to");
        message.setSubject(subject);
        message.setFrom(FIXHUB_EMAIL);
        message.setTo(to);

        // Create the HTML body using Thymeleaf
        final String htmlContent = htmlTemplateEngine.process(template, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        mailSender.send(mimeMessage);
        LOGGER.info("Sent email with subject {} from {} to {} using template {}", subject, FIXHUB_EMAIL, to, template);
    }

    @Async
    @Override
    public void sendVerificationEmail(User user, VerificationToken token, Locale locale) {
        try {
            LOGGER.debug("Sending user {} verification token", user.getId());
            final String url = appBaseUrl.toString() + "/user/verify?token=" + token.getValue();
            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, user verification mail not sent");
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(User user, PasswordResetToken token, Locale locale) {
        try {
            final String url = appBaseUrl.toString() + "/user/resetPassword?token=" + token.getValue();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, user password reset mail not sent");
        }
    }

    @Async
    @Override
    public void sendProviderNotificationEmail(User user, Locale locale) {
        try {
            final String newJobURL = appBaseUrl.toString() + "jobs/new";

            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("newJobURL", newJobURL);

            mailAttrs.put("to", user.getEmail());
            mailAttrs.put("name", user.getName());
            sendMail("providerNotification", messageSource.getMessage("email.providerNotification", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, provider notification mail not sent");
        }
    }


    @Async
    @Override
    public void sendJobRequestConfirmationEmail(JobContact jobContact, Locale locale) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String providerURL = appBaseUrl.toString() + "/user/" + jobContact.getJob().getProvider().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();
            mailAttrs.put("requestURL", requestURL);

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);

            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobRequestConfirmation", messageSource.getMessage("email.jobRequestConfirmation", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job request sent confirmation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobCancellationEmail(JobContact jobContact, Locale locale) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String providerURL = appBaseUrl.toString() + "/user/" + jobContact.getJob().getProvider().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();

            mailAttrs.put("requestURL", requestURL);

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobCancellation", messageSource.getMessage("email.jobCancellation", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job cancellation mail not sent");
        }
    }

    @Async
    @Override
    public void sendUserJobCancellationEmail(JobContact jobContact, Locale locale) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String userURL = appBaseUrl.toString() + "/user/" + jobContact.getUser().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();

            mailAttrs.put("requestURL", requestURL);

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("userURL", userURL);
            mailAttrs.put("to", jobContact.getProvider().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("userName", jobContact.getUser().getName());
            mailAttrs.put("name", jobContact.getJob().getProvider().getName());

            sendMail("userJobCancellation", messageSource.getMessage("email.jobCancellationUser", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job cancellation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobConfirmationEmail(JobContact jobContact, Locale locale) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String providerURL = appBaseUrl.toString() + "/user/" + jobContact.getJob().getProvider().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();

            mailAttrs.put("requestURL", requestURL);

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobConfirmation", messageSource.getMessage("email.jobConfirmation", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job confirmation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobFinishedEmail(JobContact jobContact, Locale locale) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String providerURL = appBaseUrl.toString() + "/user/" + jobContact.getJob().getProvider().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();

            mailAttrs.put("requestURL", requestURL);

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobFinished", messageSource.getMessage("email.jobFinished", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job confirmation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobRequestEmail(JobContact jobContact, Locale locale) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = appBaseUrl.toString() + "/jobs/" + jobContact.getJob().getId();
            final String userURL = appBaseUrl.toString() + "/user/" + jobContact.getUser().getId();
            final String requestURL = appBaseUrl.toString() + "/user/requests/" + jobContact.getId();

            mailAttrs.put("requestURL", requestURL);

            final ContactInfo contactInfo = jobContact.getContactInfo();

            final String address = String.format("%s, %s, %s %s, %s %s",
                contactInfo.getState(), contactInfo.getCity(),
                contactInfo.getStreet(), contactInfo.getAddressNumber(),
                contactInfo.getFloor(), contactInfo.getDepartmentNumber());

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("userURL", userURL);
            mailAttrs.put("to", jobContact.getJob().getProvider().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());
            mailAttrs.put("surname", jobContact.getUser().getSurname());
            mailAttrs.put("email", jobContact.getUser().getEmail());
            mailAttrs.put("address", address);
            mailAttrs.put("phoneNumber", jobContact.getUser().getPhoneNumber());
            mailAttrs.put("message", jobContact.getMessage());

            sendMail("jobRequest", messageSource.getMessage("email.jobRequest", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, Job request mail not sent");
        }
    }
}
