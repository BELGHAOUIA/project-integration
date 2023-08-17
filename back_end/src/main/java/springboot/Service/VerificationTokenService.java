package springboot.Service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.Entity.IUser;
import springboot.Entity.VerificationToken;
import springboot.Repository.VerificationTokenRepository;
import springboot.Service.mail.EmailService;
import springboot.Service.registration.EmailVerificationService;
import springboot.auth.ApiResponse;

import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VerificationTokenService {

    @Autowired
    VerificationTokenRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    EmailVerificationService emailVerificationService;

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    @Value("${jdj.secure.token.validity}")
    private int tokenValidityInSeconds;

    public ResponseEntity<?> createVerificationToken(IUser user){
        return sendEmail(user, generateVerificationToken(user));
    }

    public ResponseEntity<?> createVerificationTokenForResetPassword(IUser user){
        return resetPasswordEmail(user, generateVerificationToken(user));
    }

    public String generateVerificationToken(IUser user){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()),
                US_ASCII); // this is a sample, you can adapt as per your security need
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(tokenValue);
        verificationToken.setExpireAt(LocalDate.from(LocalDateTime.now()
                .plusSeconds(this.getTokenValidityInSeconds())));
        verificationToken.setUser(user);
        saveSecureToken(verificationToken);

        return tokenValue;
    }

    public void saveSecureToken(VerificationToken token) {
        repository.save(token);
    }

    public VerificationToken findByToken(String token) {
        return repository.findByToken(token);
    }

    public void removeToken(VerificationToken token) {
        repository.delete(token);
    }

    public void removeTokenByToken(String token) {
        repository.removeByToken(token);
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }


    public ResponseEntity<?> sendEmail(IUser request, String token) {
        boolean isValidEmail = emailService.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String link = "http://localhost:8081/#/loginForm?token=" + token;
        try {
            emailService.sendVerificationEmail(
                        request.getEmail(),
                        buildEmail(request.getUserName(), link,
                                "Confirmez votre adresse e-mail",
                                "Merci de votre inscription. Veuillez cliquer sur le lien ci-dessous pour activer votre compte :",
                                "Activer maintenant"));

        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(new ApiResponse(true,
                "email sent successfully"),
                HttpStatus.CREATED);
    }

    public ResponseEntity<?> resetPasswordEmail(IUser request, String token) {
        boolean isValidEmail = emailService.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String link = "http://localhost:8081/#/forgotPassword?token=" + token;
        try {
            emailService.sendVerificationEmail(
                    request.getEmail(),
                    buildEmail(request.getUserName(), link,
                            "Réinitialiser votre mot de passe",
                            "Veuillez cliquer sur le lien ci-dessous pour réinitialiser votre mot de passe :",
                            "Réinitialiser maintenant"));

        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(new ApiResponse(true,
                "email sent successfully"),
                HttpStatus.CREATED);
    }

    @Transactional
    public String confirmToken(String token) {
        VerificationToken confirmationToken = findByToken(token);
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));

        if (confirmationToken.isExpired()) {
            throw new IllegalStateException("token expired");
        }
                  removeToken(confirmationToken);

        emailVerificationService.enableUser(
                confirmationToken.getUser().getEmail());

        return "confirmed";
    }

    @Transactional
    public String confirmTokenAndChangeUserPassword(String token, String password) {
        VerificationToken confirmationToken = findByToken(token);
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));

        if (confirmationToken.isExpired()) {
            throw new IllegalStateException("token expired");
        }
                   removeToken(confirmationToken);

        emailVerificationService.changePassword(
                confirmationToken.getUser().getEmail(), password);

        return "confirmed";
    }

    private String buildEmail(String name, String link, String title, String message, String button) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">"+ title +"</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  " + message + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">" + button + "</a> </p></blockquote>\n Le lien expirera dans 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    public ResponseEntity<?> verifyToken(String token) {
        if(confirmToken(token).equals("confirmed")) {
            removeTokenByToken(token);
            return new ResponseEntity<>(new ApiResponse(true,"Succes de Registration"),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(false,"Echec de Registration"),
                HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<?> changePassword(String token, String newPassword) {
        if(confirmTokenAndChangeUserPassword(token, newPassword).equals("confirmed")) {
            removeTokenByToken(token);
            return new ResponseEntity<>(new ApiResponse(true,
                    "Succes de changement de mot de passe"),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(false,
                "Echec de changement de mot de passe"),
                HttpStatus.NOT_ACCEPTABLE);
    }

}
