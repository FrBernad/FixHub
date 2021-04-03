package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/emailSimple";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Override
    public void sendSimpleMail(final String userName,
                               final String userSurname,
                               final String userAddress,
                               final String userPhoneNumber,
                               final String userMessage,
                               final String providerEmail,
                               Locale locale) throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("userName", userName);
        ctx.setVariable("userSurname", userSurname);
        ctx.setVariable("userAddress", userAddress);
        ctx.setVariable("userPhoneNumber", userPhoneNumber);
        ctx.setVariable("userMessage", userMessage);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Solicitud de trabajo Fixhub");
        message.setFrom("fixhubcompany@gmail.com");
        message.setTo(providerEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        mailSender.send(mimeMessage);
    }
}
