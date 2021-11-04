package karol.wlazlo.ds.update.services;

import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Slf4j
@Service
@EnableAsync
public class EmailService {

    private final String host = "smtp.mailtrap.io";
    private final String from = "";
    private final String password = "";

    @Async
    public void sendActivateEmail(AppUser user) {
        Session session = setUp();

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(user.getEmail());
            message.addRecipient(Message.RecipientType.TO, toAddress[0]);

            message.setSubject("Aktywacja konta");
            message.setText("Kliknij w poniższy link aby aktywować konto: \n" + generateActivateLink(user.getActivateAccountUUID().toString(), String.valueOf(user.getId())));

            Transport transport = session.getTransport("smtp");

            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException | MalformedURLException ae) {
            ae.printStackTrace();
        }
    }

    @Async
    public void sendResetEmail(AppUser user, String tempPassword) {
        Session session = setUp();

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(user.getEmail());
            message.addRecipient(Message.RecipientType.TO, toAddress[0]);

            message.setSubject("Resetowanei hasła");
            message.setText("Hasło tymczasowe: " + tempPassword + "\n" + "Kliknij w poniższy link aby zresetować hasło: \n" + generateResetPasswordLink(user.getResetPasswordUUID().toString(), String.valueOf(user.getId())));

            Transport transport = session.getTransport("smtp");

            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException | MalformedURLException ae) {
            ae.printStackTrace();
        }
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
