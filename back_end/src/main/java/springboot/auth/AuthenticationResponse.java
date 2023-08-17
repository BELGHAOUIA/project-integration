package springboot.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import springboot.Entity.Enum.Role;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final  String  role;
    private  final String userName;
    private  final String email;
}
