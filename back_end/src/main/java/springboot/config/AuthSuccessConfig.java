package springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import springboot.Service.EmploiDeTempsService;
import springboot.Service.SalleService;
import springboot.Service.mail.EmailService;

import javax.mail.MessagingException;

@Configuration
public class AuthSuccessConfig {

    @Autowired
    private SalleService salleService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmploiDeTempsService emploiDeTempsService;

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authSuccessListener() {
        return event -> {
            salleService.start();
            emploiDeTempsService.start();
        };
    }
}