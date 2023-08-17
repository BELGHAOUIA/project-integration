package springboot.RestController.impl;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.Entity.Actif;
import springboot.customResponses.CustomActifResponse;

@RequestMapping("/actif/")
public interface ActifControllerImpl {
	
	@PostMapping("add") 
	public Actif add(@RequestBody Actif a);
	
	@DeleteMapping("delete")
	public boolean delete(@RequestParam(name = "id") long id);
	
	@PutMapping("update")
	public boolean update(@RequestParam(name="id") long id,@RequestBody Actif a);
	
	@GetMapping("fetchAll")
	public List<CustomActifResponse> fetchAll();

	@GetMapping("fetchAllBySalleId")
	public List<Actif> fetchAllBySalleId(@RequestParam(name="id") long id);

}
