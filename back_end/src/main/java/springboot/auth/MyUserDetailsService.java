package springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.Entity.IUser;
import springboot.Service.UserService;
import springboot.exceptions.EntityNotFoundException;
import springboot.exceptions.ErrorCodes;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<IUser> user = userService.findByEmail(email);
        user.orElseThrow(() ->
                new EntityNotFoundException("aucun utilisateur avec l'email fourni", ErrorCodes.USER_NOT_FOUND)
        );
        return user.map(MyUserDetails::new).get();
    }
}
