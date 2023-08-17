package springboot.RestController.impl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot.auth.AuthenticationRequest;
import springboot.customRequests.SignUpRequest;
import springboot.customResponses.CustomUserDetailsResponse;

import java.util.List;

@RequestMapping("/user/")
public interface UserControllerImpl {
        @PostMapping("register/etudiant")
        public ResponseEntity<?> registerEtudiant(@RequestBody SignUpRequest etudiant);

        @PostMapping("register/prof")
        public ResponseEntity<?> registerProf(@RequestBody SignUpRequest prof);

        @PostMapping("authenticate")
        public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception;

        @GetMapping("userNames/fetchAll")
        public List<String> findUserNamesByRoleProf();

        @PutMapping(value = "profilePicture/put")
        public boolean updateProfilePicture(@AuthenticationPrincipal UserDetails userDetails, @RequestPart MultipartFile file);

        @GetMapping(value = "profilePicture/fetch", produces = MediaType.IMAGE_PNG_VALUE)
        public ResponseEntity<InputStreamResource> fetchProfilePicture(@AuthenticationPrincipal UserDetails userDetails);

        @GetMapping(value = "targetUser/profilePicture/fetch", produces = MediaType.IMAGE_PNG_VALUE)
        public ResponseEntity<InputStreamResource> fetchTargetUserProfilePicture(@RequestParam("email") String email);

        @GetMapping(value = "userDetails/fetch")
        public CustomUserDetailsResponse fetchUserDetails(@AuthenticationPrincipal UserDetails userDetails);

        @GetMapping(value = "prof/userDetails/fetchAll")
        public List<CustomUserDetailsResponse> fetchAllProffesseurUserDetails();

        @DeleteMapping("prof/delete")
        public boolean deleteProf(@RequestParam("email") String email);

}
