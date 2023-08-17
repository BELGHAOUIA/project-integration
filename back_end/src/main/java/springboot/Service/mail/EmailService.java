package springboot.Service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import springboot.Service.TicketService;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService implements Predicate<String> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TicketService ticketService;

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void sendEmail(long to, String subject, String text) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(ticketService.findById(to).getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String to, String text) throws MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setHeader("Content-Type", "text/html");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Verification d'email:");
            helper.setText(text, true); // set the second argument to true to indicate HTML content
            mailSender.send(message);
            System.out.println("email sent successfully");
        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean test(String s) {
        if (s == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(s);
        return matcher.matches();
    }
}