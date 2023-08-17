package springboot.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springboot.Entity.Logiciel;
import springboot.RestController.impl.LogicielControllerImpl;
import springboot.Service.LogicielService;


@CrossOrigin
@RestController
public class LogicielController implements LogicielControllerImpl {
	LogicielService service;
	
	@Autowired
	public LogicielController(LogicielService service) {
		this.service = service;
	}
	
	@Override
	public Logiciel add(Logiciel a) {
		return service.add(a);
	}
	
	@Override
	public boolean delete(long id) {
		service.delete(id);
		return true;
	}
	
	@Override
	public boolean update(long id, Logiciel a) {
		service.update(id,a);
		return true;
	}
	
	@Override
	public List<Logiciel> fetchAll() {
		return service.fetchAll();
	}
	
	@Override
    public ResponseEntity<InputStreamResource> getImage(long id) {
        return service.getImage(id);
    }
	
	@Override
	public boolean putLogo(String nom, MultipartFile file) {
		service.putLogo(nom, file);
		return true;
	}
}
