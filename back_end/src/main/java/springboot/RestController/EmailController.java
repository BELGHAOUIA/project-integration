package springboot.RestController;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springboot.Entity.IUser;
import springboot.RestController.impl.EmailControllerImpl;
import springboot.Service.UserService;
import springboot.Service.mail.EmailService;
import springboot.auth.MyUserDetailsService;

import java.util.Optional;

@CrossOrigin
@RestController
public class EmailController implements EmailControllerImpl {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserService userService;

    @Override
    public String sendEmail(long to,
                            String subject,
                            String text,
                            UserDetails currentUser) throws MessagingException {
        Optional<IUser> user = userService.findByEmail(currentUser.getUsername());
        String from = user.get().getUserName();
        emailService.sendEmail(to, subject, "From: " + from + "\n\n" + text);
        return "email-sent";
    }
}