package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import springboot.Entity.EmploiDeTemps;
import springboot.RestController.impl.EmploiDeTempsControllerImpl;
import springboot.Service.EmploiDeTempsService;

import java.util.List;
@CrossOrigin
@RestController
public class EmploiDeTempsController implements EmploiDeTempsControllerImpl {

    private EmploiDeTempsService service;

    @Autowired
    public EmploiDeTempsController(EmploiDeTempsService service) {
        this.service = service;
    }

    @Override
    public void start() {
         service.start();
    }

    @Override
    public boolean update(String numeroSalle,List<EmploiDeTemps> emploiDeTempsList) {
        return service.update(numeroSalle,emploiDeTempsList);
    }

    @Override
    public List<EmploiDeTemps> fetchAll(String numeroSalle) {
        return service.fetchAll(numeroSalle);
    }
}
