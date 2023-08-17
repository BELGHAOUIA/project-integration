package springboot.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import springboot.Entity.Enum.Role;
import springboot.Entity.IUser;
import springboot.Repository.UserRepository;
import springboot.auth.ApiResponse;
import springboot.customRequests.SignUpRequest;
import springboot.customResponses.CustomUserDetailsResponse;

@Service
public class UserService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public ResponseEntity<?> registerProf(SignUpRequest prof) {
		List<Role> roles = new ArrayList<>();
		roles.add(Role.PROF);
		return registerUser(prof,roles);
	}

	public ResponseEntity<?> registerEtudiant(SignUpRequest etudiant) {
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ETUDIANT);
		return registerUser(etudiant,roles);
	}

	public ResponseEntity<?> registerUser(SignUpRequest signupRequest, List<Role> roles) {

		if (existsByUsername(signupRequest.getUserName())) {
			return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (existsByEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		IUser user = new IUser();
		user.setUserName(signupRequest.getUserName());
		user.setEmail(signupRequest.getEmail());
		try {
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println(signupRequest.getUserName());
		}
		if(roles.contains(Role.ETUDIANT)) {
			user.setActive(false);
		} else user.setActive(true);

		// Creating user's roles and authorities
		user.setRoles(roles);

		byte[] photoBytes = readPhotoFromFile("defaultProfilePicture.jpg");
		user.setProfilePicture(photoBytes);

		repository.save(user);

		return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
	}

	public byte[] readPhotoFromFile(String filePath) {
        try {
            Path path = Paths.get("C:\\Users\\user\\Downloads\\pictures\\" + filePath);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	

	public boolean existsByUsername(String userName) {
		Optional<IUser> user = repository.findByUserName(userName);
		if(user.isPresent()) return true;
		return false;
	}

	public boolean existsByEmail(String email) {
		Optional<IUser> user = repository.findByEmail(email);
		if(user.isPresent()) return true;
		return false;
	}

	public List<IUser> findAll() {
		return repository.findAll();
	}

	public Optional<IUser> findByEmail(String email) {
		System.out.println(repository.findByEmail(email));
		return repository.findByEmail(email);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

    public List<String> findUserNamesByRoleProf() {
		List<String> names = repository.findUserNamesByRoleProf();
		if(names.contains("Matrix Beji")) names.remove("Matrix Beji");
		Collections.sort(names);
		return names;
    }

    public boolean updateProfilePicture(String email, MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			IUser user = repository.findByEmail(email).get();
			user.setProfilePicture(bytes);
			repository.save(user);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return true;
    }

	public ResponseEntity<InputStreamResource> fetchProfilePicture(String email) {
		Optional<IUser> user = repository.findByEmail(email);
		InputStream is = new ByteArrayInputStream(user.get().getProfilePicture());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
	}

	public CustomUserDetailsResponse fetchUserDetails(String email) {
		IUser user = repository.findByEmail(email).get();
		return new CustomUserDetailsResponse(
				user.getUserName(),
				user.getEmail(),
				null);
	}

	public void enableUser(String email) {
		IUser user = repository.findByEmail(email).get();
		user.setActive(true);
		repository.save(user);
	}

	public void changePassword(String email, String password) {
		IUser user = repository.findByEmail(email).get();
		user.setPassword(passwordEncoder.encode(password));
		repository.save(user);
	}

    public List<CustomUserDetailsResponse> fetchAllProffesseurUserDetails() {
		List<IUser> users = repository.findAll();
		List<CustomUserDetailsResponse> usersDetails = new LinkedList<>();
		for (IUser user: users) {
			if(/*user.getRoles().contains(Role.PROF) && */!user.getRoles().contains(Role.TECHNICIEN)) {
				usersDetails.add(new CustomUserDetailsResponse(
						user.getUserName(),
						user.getEmail(),
						null));
			}
		}
		return usersDetails;
    }

	public boolean deleteByEmail(String email) {
		IUser user = repository.findByEmail(email).get();
		repository.deleteById(user.getId());
		return true;
	}
}
