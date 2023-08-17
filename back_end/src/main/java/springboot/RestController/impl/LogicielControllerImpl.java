package springboot.RestController.impl;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import springboot.Entity.Logiciel;

@RequestMapping("/logiciel/")
public interface LogicielControllerImpl {
	
	@PostMapping("add") 
	public Logiciel add(@RequestBody Logiciel a) ;
	
	@DeleteMapping("delete")
	public boolean delete(@RequestParam(name = "id") long id);
	
	@PutMapping("update")
	public boolean update(@RequestParam(name="id") long id,@RequestBody Logiciel a);
	
	@GetMapping("fetchAll")
	public List<Logiciel> fetchAll();
	
	@GetMapping(value = "photo/fetch", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(name="id") long id);
	
	@PutMapping(value = "logo/put")
	public boolean putLogo(@RequestParam(name="nom") String nom,@RequestPart MultipartFile file);

}
