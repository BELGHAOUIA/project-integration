package springboot.RestController.impl;

import org.springframework.web.bind.annotation.*;
import springboot.Entity.EmploiDeTemps;

import java.util.List;

@RequestMapping("/emploi/")
public interface EmploiDeTempsControllerImpl {
    @PostMapping("start")
    public void start() ;

    @PutMapping("update")
    public boolean update(@RequestParam("numeroSalle") String numeroSalle,@RequestBody List<EmploiDeTemps> emploiDeTempsList);

    @GetMapping("fetchAllByNumeroSalle")
    public List<EmploiDeTemps> fetchAll(@RequestParam("numeroSalle") String numeroSalle);
}
