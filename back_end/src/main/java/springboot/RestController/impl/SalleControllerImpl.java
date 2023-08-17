package springboot.RestController.impl;

import java.util.List;

import jdk.jfr.ContentType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.Entity.Actif;
import springboot.Entity.Logiciel;
import springboot.Entity.Salle;

@RequestMapping("/salle/")
public interface SalleControllerImpl {
	
	@PostMapping("start")
	public boolean starter() throws RuntimeException;
	
	@PostMapping("add") 
	public Salle add(@RequestBody Salle a);
	
	@DeleteMapping("delete")
	public void delete(@RequestParam(name = "id") long id);
	
	@PutMapping("update")
	public boolean update(@RequestParam(name="id") long id,@RequestBody Salle a);

	@GetMapping("projecteur/fetch")
	public boolean getDataShow(@RequestParam(name = "numeroSalle") String numeroSalle);
	
	@PutMapping("projecteur/update")
	public boolean updateDataShow(@RequestParam(name = "numeroSalle") String numeroSalle, @RequestParam(name = "value") boolean value);
	
	@GetMapping("actif/fetchAll")
	public List<Actif> fetchAllActifs(@RequestParam(name = "numeroSalle") String numeroSalle) ;
	
	@GetMapping("logiciel/fetchAll")
	public List<Logiciel> fetchAllLogiciels(@RequestParam(name = "numeroSalle") String numeroSalle);
	
	@PutMapping("logiciel/add")
	public boolean addLogiciel(@RequestParam(name="numeroSalle") String numeroSalle,@RequestBody Logiciel logiciel);

	@PutMapping(value = "actif/add")
	public boolean addActif(@RequestParam(name="numeroSalle") String numeroSalle,
							@RequestBody Actif actif);
	
	@PutMapping("actif/delete")
	public boolean deleteActif(@RequestParam(name="numeroSalle") String numeroSalle, @RequestParam(name="actifId") long actifId) throws Exception;

	@PutMapping("logiciel/delete")
	public boolean deletelogiciel(@RequestParam(name="numeroSalle") String numeroSalle, @RequestParam(name="logicielId") long logicielId) throws Exception;

	@GetMapping("nombreTable/fetch")
	public int fetchNombreTable(@RequestParam(name="numeroSalle") String numeroSalle);
	
	@PutMapping("nombreTable/update")
	public boolean updateNombreTable(@RequestParam(name="numeroSalle") String numeroSalle,@RequestParam(name="nombre") int nombre);

	@GetMapping("fetchAll")
	public List<Salle> fetchAll();

}
