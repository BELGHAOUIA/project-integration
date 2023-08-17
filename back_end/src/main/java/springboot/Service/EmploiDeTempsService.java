package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Entity.EmploiDeTemps;
import springboot.Entity.Enum.Jour;
import springboot.Repository.EmploiDeTempsRepository;
import springboot.Repository.SalleRepository;

import java.util.List;

@Service
public class EmploiDeTempsService {

    @Autowired
    EmploiDeTempsRepository repository;

    @Autowired
    SalleRepository salleRepository;
    public void start() {
        if(repository.count() == 0) {
            for (String numeroSalle: salleRepository.fetchNumeroSalle()) {
                EmploiDeTemps emploi1 = new EmploiDeTemps();
                emploi1.setJour(Jour.Lundi);
                emploi1.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi1);

                EmploiDeTemps emploi2 = new EmploiDeTemps();
                emploi2.setJour(Jour.Mardi);
                emploi2.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi2);

                EmploiDeTemps emploi3 = new EmploiDeTemps();
                emploi3.setJour(Jour.Mercredi);
                emploi3.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi3);

                EmploiDeTemps emploi4 = new EmploiDeTemps();
                emploi4.setJour(Jour.Jeudi);
                emploi4.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi4);

                EmploiDeTemps emploi5 = new EmploiDeTemps();
                emploi5.setJour(Jour.Vendredi);
                emploi5.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi5);

                EmploiDeTemps emploi6 = new EmploiDeTemps();
                emploi6.setJour(Jour.Samedi);
                emploi6.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
                repository.save(emploi6);
            }
        }
    }

    public boolean update(String numeroSalle, List<EmploiDeTemps> emploiDeTempsList) {
        for (EmploiDeTemps emploi: emploiDeTempsList) {
            emploi.setSalle(salleRepository.findByNumeroSalle(numeroSalle));
        }
        repository.saveAll(emploiDeTempsList);
        return true;
    }

    public List<EmploiDeTemps> fetchAll(String numeroSalle) {
        return repository.fetchAllBySalleId(numeroSalle);
    }
}
