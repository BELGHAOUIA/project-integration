package springboot.customRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {
    private String userName;
    private String email;
    private String password;
}
