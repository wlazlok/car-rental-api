package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.model.ContactForm.ContactForm;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Slf4j
@Service
@EnableAsync
public class EmailService {

    private final String host = "smtp.mailtrap.io";
    private final String from = "c3522abe840c72";
    private final String password = "5793fa0149eaa4";

    private final String EMAIL_FROM = "test@test.com";
    private final String TEMPLATE_NAME_ACTIVATE = "registration";
    private final String TEMPLATE_NAME_RESET = "resetPassword";
    private final String TEMPLATE_CONTACT_FORM = "contact";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendActivateEmail(AppUser user) {
        try {
            MimeMessage message = generateActivateEmail(user);

            mailSender.send(message);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    public void sendResetEmail(AppUser user, String tempPassword) {
        try {
            MimeMessage message = generateResetPasswordEmail(user, tempPassword);

            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendContactEmail(ContactForm form) {
        try {
            MimeMessage message = generateContactFormEmail(form);

            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("email.service.send.contact.email.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.send.email");
        }
    }

    public MimeMessage generateContactFormEmail(ContactForm form) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();


        mailSender.createMimeMessage();
        MimeMessageHelper email;
        email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        email.setTo("test9@email.com"); //todo: po przełączeniu sie na gmail tutaj swój email
        email.setSubject("Formularz kontaktowy");
        email.setFrom(EMAIL_FROM);

        Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("firstName", form.getFirstName());
        ctx.setVariable("secondName", form.getSecondName());
        ctx.setVariable("emailAddress", form.getEmailAddress());
        ctx.setVariable("phone", form.getPhoneNumber());
        ctx.setVariable("content", form.getMessageContent());

        String htmlContent = templateEngine.process(TEMPLATE_CONTACT_FORM, ctx);

        email.setText(htmlContent, true);


        return mimeMessage;
    }

    private MimeMessage generateActivateEmail(AppUser user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            URL activateLink = generateActivateLink(user.getActivateAccountUUID().toString(), String.valueOf(user.getId()));
            mailSender.createMimeMessage();
            MimeMessageHelper email;
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject("Aktywacja konta");
            email.setFrom(EMAIL_FROM);

            Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("url", activateLink);

            String htmlContent = templateEngine.process(TEMPLATE_NAME_ACTIVATE, ctx);

            email.setText(htmlContent, true);
        } catch (Exception ex) {
            log.info("Error during creating email content");
        }

        return mimeMessage;
    }

    private MimeMessage generateResetPasswordEmail(AppUser user, String tempPassword) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            URL resetLink = generateResetPasswordLink(user.getResetPasswordUUID().toString(), String.valueOf(user.getId()));
            mailSender.createMimeMessage();
            MimeMessageHelper email;
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setSubject("Resetowanie hasła");
            email.setFrom(EMAIL_FROM);

            Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("url", resetLink);
            ctx.setVariable("tempPassword", tempPassword);

            String htmlContent = templateEngine.process(TEMPLATE_NAME_RESET, ctx);

            email.setText(htmlContent, true);
        } catch (Exception ex) {
            log.info("Error during creating email content");
        }

        return mimeMessage;
    }


    public URL generateActivateLink(String uuid, String userId) throws MalformedURLException {
        return new URL("http://localhost:3000/activate/" + uuid + "/" + userId);
    }

    public URL generateResetPasswordLink(String uuid, String userId) throws MalformedURLException {
        return new URL("http://localhost:3000/reset/password/" + uuid + "/" + userId);
    }

    private Session setUp() {
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        return Session.getDefaultInstance(props);
    }
}
