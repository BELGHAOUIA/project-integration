package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import springboot.Entity.Logiciel;
import springboot.Entity.Salle;
import springboot.Repository.LogicielRepository;
import springboot.Repository.SalleRepository;

@Service
public class SallesLogicielsService {
	
	SalleRepository salleRepository;
	
	LogicielRepository logicielRepository;

	@Autowired
	public SallesLogicielsService(SalleRepository salleRepository, LogicielRepository logicielRepository) {
		this.salleRepository = salleRepository;
		this.logicielRepository = logicielRepository;
	}

	@Transactional
	public void addLogicielToSalle(String numeroSalle, Logiciel logiciel) {
		logicielRepository.save(logiciel);
		Salle salle = salleRepository.findByNumeroSalle(numeroSalle);
		salle.getLogiciels().add(logicielRepository.findByNom(logiciel.getNom()));
		salleRepository.save(salle);
	}

	public boolean deleteLogiciel(String numeroSalle, long logicielId) {
		Salle salle = salleRepository.findByNumeroSalle(numeroSalle);
		salle.getLogiciels().remove(logicielRepository.findById(logicielId).get());
		salleRepository.save(salle);
        return true;
    }
	
	
}
