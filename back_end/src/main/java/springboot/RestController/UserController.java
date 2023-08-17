package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springboot.RestController.impl.UserControllerImpl;
import springboot.Service.UserService;
import springboot.Service.auth.AuthenticateUser;
import springboot.auth.AuthenticationRequest;
import springboot.customRequests.SignUpRequest;
import springboot.customResponses.CustomUserDetailsResponse;

import java.util.List;

@CrossOrigin
@RestController
public class UserController implements UserControllerImpl {
	UserService service;

	AuthenticateUser authenticateUser;

	@Autowired
	public UserController(UserService service, AuthenticateUser authenticateUser) {
		this.service = service;
		this.authenticateUser = authenticateUser;
	}

	@Override
	public ResponseEntity<?> registerEtudiant(SignUpRequest etudiant) {
		return service.registerEtudiant(etudiant);
	}

	@Override
	public ResponseEntity<?> registerProf(SignUpRequest prof) {
		return service.registerProf(prof);
	}

	@Override
	public ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest) throws Exception {
		return authenticateUser.authenticateUser(authenticationRequest);
	}

	@Override
	public List<String> findUserNamesByRoleProf() {
		return service.findUserNamesByRoleProf();
	}

	@Override
	public boolean updateProfilePicture(UserDetails userDetails, MultipartFile file) {
		return service.updateProfilePicture(userDetails.getUsername(),file);
	}

	@Override
	public ResponseEntity<InputStreamResource> fetchProfilePicture(UserDetails userDetails) {
		return service.fetchProfilePicture(userDetails.getUsername());
	}

	@Override
	public ResponseEntity<InputStreamResource> fetchTargetUserProfilePicture(String email) {
		return service.fetchProfilePicture(email);
	}

	@Override
	public CustomUserDetailsResponse fetchUserDetails(UserDetails userDetails) {
		return service.fetchUserDetails(userDetails.getUsername());
	}

	@Override
	public List<CustomUserDetailsResponse> fetchAllProffesseurUserDetails() {
		return service.fetchAllProffesseurUserDetails();
	}

	@Override
	public boolean deleteProf(String email) {
		return service.deleteByEmail(email);
	}

}
