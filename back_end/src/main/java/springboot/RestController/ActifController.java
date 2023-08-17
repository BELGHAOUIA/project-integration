package springboot.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entity.Actif;
import springboot.RestController.impl.ActifControllerImpl;
import springboot.Service.ActifService;
import springboot.customResponses.CustomActifResponse;

@CrossOrigin
@RestController
public class ActifController implements ActifControllerImpl{
	
	ActifService service;
		
	@Autowired
	public ActifController(ActifService service) {
		this.service = service;
	}

	@Override
	public Actif add(Actif a) {
		return service.add(a);
	}
	
	@Override
	public boolean delete(long id) {
		service.delete(id);
		return true;
	}
	
	@Override
	public boolean update(long id, Actif a) {
		service.update(id,a);
		return true;
	}

	@Override
	public List<CustomActifResponse> fetchAll() {
		return service.fetchAll();
	}

	@Override
	public List<Actif> fetchAllBySalleId(long id) {
		return service.fetchAllBySalleId(id);
	}

}
