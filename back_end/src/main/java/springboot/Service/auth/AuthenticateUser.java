package springboot.Service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.Entity.Enum.Role;
import springboot.Repository.UserRepository;
import springboot.Service.UserService;
import springboot.auth.ApiResponse;
import springboot.auth.AuthenticationRequest;
import springboot.auth.AuthenticationResponse;
import springboot.auth.MyUserDetailsService;
import springboot.exceptions.EntityNotFoundException;
import springboot.exceptions.ErrorCodes;
import springboot.utils.JwtUtil;

import java.util.LinkedList;
import java.util.List;

@Service
public class AuthenticateUser {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    UserService userService;

    public ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        // if (userDetails == null) {
        //     return ResponseEntity.ok(new ApiResponse(false, "Adresse e-mail ou mot de passe incorrecte."));
        // }

        final String jwt = jwtUtil.generateToken(userDetails);

        String role = "";
        SimpleGrantedAuthority tech = new SimpleGrantedAuthority(Role.TECHNICIEN.toString());
        SimpleGrantedAuthority prof = new SimpleGrantedAuthority(Role.PROF.toString());
        SimpleGrantedAuthority etud = new SimpleGrantedAuthority(Role.ETUDIANT.toString());
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals(tech.getAuthority())) {
                role = Role.TECHNICIEN.toString();
                break;
            } else if (authority.getAuthority().equals(prof.getAuthority())) {
                role = Role.PROF.toString();
                break;
            } else if (authority.getAuthority().equals(etud.getAuthority())) {
                role = Role.ETUDIANT.toString();
                break;
            }
        }
        if (role == null) {
            // handle case where user does not have any of the expected roles
        }
        return ResponseEntity.ok(new AuthenticationResponse(jwt,role,userService.findByEmail(userDetails.getUsername()).get().getUserName() ,userDetails.getUsername()));
    }
}
