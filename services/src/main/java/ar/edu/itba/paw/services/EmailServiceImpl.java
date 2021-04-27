package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String FIXHUB_EMAIL = "fixhubcompany@gmail.com";

    @Async
    @Override
    public void sendMail(String template, String subject, Map<String, Object> variables, final Locale locale) throws MessagingException {
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
        LOGGER.info("Sent email with subject {} from {} to {} using template {}",subject,FIXHUB_EMAIL,to,template);
    }
}
