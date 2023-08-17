package springboot.customResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CustomUserDetailsResponse {
    private String userName;
    private String email;
    private String profilePicture;
}
