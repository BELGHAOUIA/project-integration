package springboot.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import springboot.Entity.Logiciel;
import springboot.Repository.LogicielRepository;

@Service
public class LogicielService {
	
	@Autowired
	LogicielRepository repository;

	public Logiciel add(Logiciel a) {
		return repository.save(a);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void update(long id, Logiciel a) {
		
	}
	
    public List<Logiciel> fetchAll() {
		return repository.findAll();
	}
	
    public ResponseEntity<InputStreamResource> getImage(long id) {
    	Optional<Logiciel> l = repository.findById(id);
        InputStream is = new ByteArrayInputStream(l.get().getPhoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
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
	
	public void start() {
		byte[] photoBytes ;
		Logiciel l1 = new Logiciel();
		l1.setNom("POSTMAN");
		photoBytes = readPhotoFromFile("postman.jpg");
		l1.setPhoto(photoBytes);
		repository.save(l1);
		
		Logiciel l2 = new Logiciel();
		l2.setNom("ORACLE");
		photoBytes = readPhotoFromFile("oracle.png");
		l2.setPhoto(photoBytes);
		repository.save(l2);
		
		Logiciel l3 = new Logiciel();
		l3.setNom("VISUAL STUDIO CODE");
		photoBytes = readPhotoFromFile("visualStudioCode.jpg");
		l3.setPhoto(photoBytes);
		repository.save(l3);
		
		Logiciel l4 = new Logiciel();
		l4.setNom("SPRING TOOL SUITE");
		photoBytes = readPhotoFromFile("springToolSuite.png");
		l4.setPhoto(photoBytes);
		repository.save(l4);
		
		Logiciel l5 = new Logiciel();
		l5.setNom("CODE BLOCKS");
		photoBytes = readPhotoFromFile("codeBlocks.jpg");
		l5.setPhoto(photoBytes);
		repository.save(l5);
		
		Logiciel l6 = new Logiciel();
		l6.setNom("WAMP SERVER");
		photoBytes = readPhotoFromFile("wampServer.png");
		l6.setPhoto(photoBytes);
		repository.save(l6);
		
		Logiciel l7 = new Logiciel();
		l7.setNom("ADOBE PHOTOSHOP");
		photoBytes = readPhotoFromFile("AdobePhotoshop.png");
		l7.setPhoto(photoBytes);
		repository.save(l7);
		
		Logiciel l8 = new Logiciel();
		l8.setNom("ADOBE XD");
		photoBytes = readPhotoFromFile("AdobeXD.jpg");
		l8.setPhoto(photoBytes);
		repository.save(l8);
		
		Logiciel l9 = new Logiciel();
		l9.setNom("ADOBE ILLUSTRATOR");
		photoBytes = readPhotoFromFile("adobeIllustrator.png");
		l9.setPhoto(photoBytes);
		repository.save(l9);
		
		Logiciel l10 = new Logiciel();
		l10.setNom("ECLIPSE");
		photoBytes = readPhotoFromFile("eclipse.jpg");
		l10.setPhoto(photoBytes);
		repository.save(l10);
		
		Logiciel l11 = new Logiciel();
		l11.setNom("MATLAB");
		photoBytes = readPhotoFromFile("matlab.jpg");
		l11.setPhoto(photoBytes);
		repository.save(l11);
		
		Logiciel l12 = new Logiciel();
		l12.setNom("ORACLE VM VIRTUAL BOX");
		photoBytes = readPhotoFromFile("oracleVmVirtualBox.jpg");
		l12.setPhoto(photoBytes);
		repository.save(l12);
		
		Logiciel l13 = new Logiciel();
		l13.setNom("PACKET TRACER");
		photoBytes = readPhotoFromFile("packetTracer.jpg");
		l13.setPhoto(photoBytes);
		repository.save(l13);
	}

	public void putLogo(String nom, MultipartFile logo) {
		try {
			byte[] bytes = logo.getBytes();
			Logiciel a = repository.findByNom(nom);
			a.setPhoto(bytes);
			repository.save(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
