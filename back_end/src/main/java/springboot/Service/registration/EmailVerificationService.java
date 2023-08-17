package springboot.Service.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Service.UserService;

@Service
public class EmailVerificationService {

    @Autowired
    UserService userService;
    public void enableUser(String email) {
        userService.enableUser(email);
    }

    public void changePassword(String email, String password) {
        userService.changePassword(email, password);
    }
}