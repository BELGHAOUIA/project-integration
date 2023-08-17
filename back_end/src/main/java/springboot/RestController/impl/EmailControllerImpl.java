package springboot.RestController.impl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

public interface EmailControllerImpl {
    @PostMapping("/email")
    public String sendEmail(@RequestParam("ticketId") long to,
                            @RequestParam("subject") String subject,
                            @RequestParam("text") String text,
                            @AuthenticationPrincipal UserDetails currentUser) throws MessagingException;
}
