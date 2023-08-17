package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import springboot.Entity.Actif;
import springboot.Entity.Salle;
import springboot.Repository.ActifRepository;
import springboot.Repository.SalleRepository;

@Service
public class SalleActifsService {
	
	SalleRepository salleRepository;

	ActifRepository actifRepository;

	@Autowired
	public SalleActifsService(SalleRepository salleRepository, ActifRepository actifRepository) {
		this.salleRepository = salleRepository;
		this.actifRepository = actifRepository;
	}

	@Transactional
	public void addActifInSalle(String numeroSalle,Actif actif) {
		actifRepository.save(actif);
		Salle salle = salleRepository.findByNumeroSalle(numeroSalle);
		salle.getActifs().add(actifRepository.findByReference(actif.getReference()));
		//salle.getActifs().add(actif);
		salleRepository.save(salle);
	}
	
	public void deleteActif(String numeroSalle, long actifId) {
		Salle salle = salleRepository.findByNumeroSalle(numeroSalle);
		salle.getActifs().remove(actifRepository.findById(actifId).get());
		salleRepository.save(salle);
	}
}
