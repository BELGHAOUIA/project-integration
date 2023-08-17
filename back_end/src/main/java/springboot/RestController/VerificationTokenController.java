package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import springboot.RestController.impl.VerificationTokenControllerImlp;
import springboot.Service.UserService;
import springboot.Service.VerificationTokenService;

@CrossOrigin
@RestController
public class VerificationTokenController implements VerificationTokenControllerImlp {

    @Autowired
    VerificationTokenService service;

    @Autowired
    UserService userService;


    @Override
    public ResponseEntity<?> createVerificationToken(String email) {
        return service.createVerificationToken(userService.findByEmail(email).get());
    }

    @Override
    public ResponseEntity<?> createTokenForResetPassword(String email) {
        return service.createVerificationTokenForResetPassword(userService.findByEmail(email).get());
    }

    @Override
    public ResponseEntity<?> completeRegistration(String token) {
        return service.verifyToken(token);
    }

    @Override
    public ResponseEntity<?> completeResetPassword(String token, String newPassword) {
        return service.changePassword(token,newPassword);
    }
}
