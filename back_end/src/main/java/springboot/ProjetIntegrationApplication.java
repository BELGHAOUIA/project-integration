package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springboot.Entity.Enum.Role;
import springboot.Entity.IUser;
import springboot.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ProjetIntegrationApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ProjetIntegrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		IUser user = null;
		byte[] photoBytes ;
		if(userRepository.count() == 0) {
			photoBytes = readPhotoFromFile("defaultProfilePicture.jpg");
			//technicien
			user = new IUser();
			user.setUserName("Matrix Beji");
			user.setEmail("technicien@gmail.com");
			String pass = passwordEncoder.encode("tech");
			user.setPassword(pass);
			user.setActive(true);
			user.setProfilePicture(photoBytes);

			List<Role> authorities = new ArrayList<>();
			authorities.add(Role.TECHNICIEN);
			authorities.add(Role.PROF);
			user.setRoles(authorities);

			userRepository.save(user);

			//prof
			String userName= null;
			for (int i = 0; i<10; i++) {
				switch (i) {
					case 0 : userName = "Bejaoui Hana";
						break;
					case 1 : userName = "Chaouch Imène";
						break;
					case 2 : userName = "Ben Salem Wiem";
						break;
					case 3 : userName = "Ben Tekaya Yosra";
						break;
					case 4 : userName = "Elyes Snoussi";
						break;
					case 5 : userName = "Hachani Nadia";
						break;
					case 6 : userName = "Bilel Zemzem";
						break;
					case 7 : userName = "Hmida Hmida";
						break;
					case 8 : userName = "Bel Hadj Jrad Imène";
						break;
					case 9 : userName = "Bra Ines";
						break;
				}

				user = new IUser();
				user.setUserName(userName);
				user.setEmail("prof" + Integer.toString(i+1) + "@gmail.com");
				pass = passwordEncoder.encode("prof");
				user.setPassword(pass);
				user.setActive(true);
				user.setProfilePicture(photoBytes);

				authorities = new ArrayList<>();
				authorities.add(Role.PROF);
				user.setRoles(authorities);

				userRepository.save(user);
			}

			//etudiant
			user = new IUser();
			user.setUserName("Belghaouia Amal");
			user.setEmail("amelzen78@gmail.com");
			pass = passwordEncoder.encode("etud");
			user.setPassword(pass);
			user.setActive(true);
			user.setProfilePicture(photoBytes);

			authorities = new ArrayList<>();
			authorities.add(Role.ETUDIANT);
			user.setRoles(authorities);

			userRepository.save(user);
		}
	}

	private static byte[] readPhotoFromFile(String filePath) {
		try {
			Path path = Paths.get("C:\\Users\\user\\Downloads\\pictures\\" + filePath);
			return Files.readAllBytes(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
