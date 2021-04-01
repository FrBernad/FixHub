package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.webapp.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class EmailSendingController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/job/contact", method = RequestMethod.POST)
    public ModelAndView jobContactEmail(@Valid @ModelAttribute("contactForm") final ContactForm form,
                                        @RequestParam(value = "recipientEmail",defaultValue = "fran@yopmail.com") final String providerEmail,
                                        final BindingResult errors, final Locale locale) throws MessagingException {

        emailService.sendSimpleMail(form.getName(),
                form.getSurname(),
                form.getAddressNumber(),
                form.getPhoneNumber(),
                form.getMessage(),
                providerEmail, locale);

        ModelAndView mav = new ModelAndView("redirect:/discover");
        return mav;
    }

}
