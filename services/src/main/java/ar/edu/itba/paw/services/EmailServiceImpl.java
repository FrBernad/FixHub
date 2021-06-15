package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.contact.ContactDto;
import ar.edu.itba.paw.models.job.JobContact;
import ar.edu.itba.paw.models.token.PasswordResetToken;
import ar.edu.itba.paw.models.token.VerificationToken;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
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
    private String appScheme;

    @Autowired
    private String appHost;

    @Autowired
    private int appPort;

    @Autowired
    private String appRootPath;

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
    public void sendVerificationEmail(User user, VerificationToken token) {
        try {
            LOGGER.debug("Sending user {} verification token", user.getId());
            final Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL(appScheme, appHost, appRootPath + "user/verifyAccount?token=" + token.getValue()).toString();
            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            sendMail("verification", messageSource.getMessage("email.verifyAccount", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user verification mail not sent");
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(User user, PasswordResetToken token) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final String url = new URL(appScheme, appHost, appPort, appRootPath + "user/resetPassword?token=" + token.getValue()).toString();
            Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("confirmationURL", url);
            mailAttrs.put("to", user.getEmail());

            sendMail("passwordReset", messageSource.getMessage("email.resetPassword", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, user password reset mail not sent");
        }
    }

    @Async
    @Override
    public void sendProviderNotificationEmail(User user) {
        try {
            final Locale locale = LocaleContextHolder.getLocale();
            final Map<String, Object> mailAttrs = new HashMap<>();
            mailAttrs.put("to", user.getEmail());
            mailAttrs.put("name", user.getName());
            sendMail("providerNotification", messageSource.getMessage("email.providerNotification", new Object[]{}, locale), mailAttrs, locale);
        } catch (MessagingException e) {
            LOGGER.warn("Error, provider notification mail not sent");
        }
    }


    @Async
    @Override
    public void sendJobRequestConfirmationEmail(ContactDto contactDto) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = new URL(appScheme, appHost, appPort, appRootPath + "jobs/" + contactDto.getJob().getId()).toString();
            final String providerURL = new URL(appScheme, appHost, appPort, appRootPath + "user/" + contactDto.getJob().getProvider().getId()).toString();

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);

            mailAttrs.put("to", contactDto.getUser().getEmail());
            mailAttrs.put("providerJob", contactDto.getJob().getJobProvided());
            mailAttrs.put("providerName", contactDto.getJob().getProvider().getName());
            mailAttrs.put("name", contactDto.getUser().getName());

            sendMail("jobRequestConfirmation", messageSource.getMessage("email.jobRequestConfirmation", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, Job request sent confirmation mail not sent");
        }
    }


    @Async
    @Override
    public void sendJobCancellationEmail(JobContact jobContact) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = new URL(appScheme, appHost, appPort, appRootPath + "jobs/" + jobContact.getJob().getId()).toString();
            final String providerURL = new URL(appScheme, appHost, appPort, appRootPath + "user/" + jobContact.getJob().getProvider().getId()).toString();

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobCancellation", messageSource.getMessage("email.jobCancellation", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, Job cancellation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobConfirmationEmail(JobContact jobContact) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = new URL(appScheme, appHost, appPort, appRootPath + "jobs/" + jobContact.getJob().getId()).toString();
            final String providerURL = new URL(appScheme, appHost, appPort, appRootPath + "user/" + jobContact.getJob().getProvider().getId()).toString();

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobConfirmation", messageSource.getMessage("email.jobConfirmation", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, Job confirmation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobFinishedEmail(JobContact jobContact) {
        Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = new URL(appScheme, appHost, appPort, appRootPath + "jobs/" + jobContact.getJob().getId()).toString();
            final String providerURL = new URL(appScheme, appHost, appPort, appRootPath + "user/" + jobContact.getJob().getProvider().getId()).toString();

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("providerURL", providerURL);
            mailAttrs.put("to", jobContact.getUser().getEmail());
            mailAttrs.put("providerJob", jobContact.getJob().getJobProvided());
            mailAttrs.put("providerName", jobContact.getJob().getProvider().getName());
            mailAttrs.put("name", jobContact.getUser().getName());

            sendMail("jobFinished", messageSource.getMessage("email.jobFinished", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, Job confirmation mail not sent");
        }
    }

    @Async
    @Override
    public void sendJobRequestEmail(ContactDto contactDto) {
        final Map<String, Object> mailAttrs = new HashMap<>();

        try {
            final String jobURL = new URL(appScheme, appHost, appPort, appRootPath + "jobs/" + contactDto.getJob().getId()).toString();
            final String userURL = new URL(appScheme, appHost, appPort, appRootPath + "user/" + contactDto.getUser().getId()).toString();

            final String address = String.format("%s, %s, %s %s, %s %s", contactDto.getState(), contactDto.getCity(),
                contactDto.getStreet(), contactDto.getAddressNumber(), contactDto.getFloor(), contactDto.getDepartmentNumber());

            mailAttrs.put("jobURL", jobURL);
            mailAttrs.put("userURL", userURL);
            mailAttrs.put("to", contactDto.getJob().getProvider().getEmail());
            mailAttrs.put("providerJob", contactDto.getJob().getJobProvided());
            mailAttrs.put("providerName", contactDto.getJob().getProvider().getName());
            mailAttrs.put("name", contactDto.getUser().getName());
            mailAttrs.put("surname", contactDto.getUser().getSurname());
            mailAttrs.put("email", contactDto.getUser().getEmail());
            mailAttrs.put("address", address);
            mailAttrs.put("phoneNumber", contactDto.getUser().getPhoneNumber());
            mailAttrs.put("message", contactDto.getMessage());

            sendMail("jobRequest", messageSource.getMessage("email.jobRequest", new Object[]{}, LocaleContextHolder.getLocale()), mailAttrs, LocaleContextHolder.getLocale());
        } catch (MessagingException | MalformedURLException e) {
            LOGGER.warn("Error, Job request mail not sent");
        }
    }
}
