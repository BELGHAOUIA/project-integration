package springboot.RestController.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/emailVerification/")
public interface VerificationTokenControllerImlp {

    @PostMapping("createToken")
    public ResponseEntity<?> createVerificationToken(@RequestParam("email") String email);

   @PostMapping("createTokenForResetPassword")
    public ResponseEntity<?> createTokenForResetPassword(@RequestParam("email") String email);

    @PutMapping("completeRegistration")
    public ResponseEntity<?> completeRegistration(@RequestParam("token") String token);

    @PutMapping("resetPassword")
    public ResponseEntity<?> completeResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword);


}
