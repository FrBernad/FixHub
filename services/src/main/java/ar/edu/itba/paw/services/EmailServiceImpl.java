package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Async
    @Override
    public void sendMail(String template, String subject, Map<String, Object> variables) throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariables(variables);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom("fixhubcompany@gmail.com");
        message.setTo((String) variables.get("to"));

        // Create the HTML body using Thymeleaf
        final String htmlContent = htmlTemplateEngine.process(template, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        mailSender.send(mimeMessage);
    }
}
