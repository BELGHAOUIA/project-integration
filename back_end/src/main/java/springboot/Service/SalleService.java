package springboot.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.Entity.Actif;
import springboot.Entity.Logiciel;
import springboot.Entity.Salle;
import springboot.Entity.Enum.TypeActif;
import springboot.Repository.ActifRepository;
import springboot.Repository.LogicielRepository;
import springboot.Repository.SalleRepository;

@Service
public class SalleService {
	
	@Autowired
	SalleRepository repository;
	
	@Autowired
	LogicielRepository lRepository;
	
	@Autowired
	LogicielService lService;
	
	@Autowired
	ActifRepository aRepository;
	
	@Autowired
	ActifService aService;
	

	public Salle add(Salle a) {
		return repository.save(a);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void update(long id, Salle a) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Salle> fetchAll() {
		return repository.findAll();
	}
	
	public void countPost() {
		List<Salle> s = repository.findAll();
		for (Salle salle : s) {
			salle.setNombrePoste(repository.countActifsBySalleId(salle.getId()));
		}
	}
	
	public List<Salle> start() {
		if(count() == 0) {
			
			lService.start();
			aService.start();
			
			
			for(int i = 0;i<11;i++) {
				Salle s = new Salle();
				if (i == 7)
					s.setNumeroSalle("GSAA");
				else if (i == 8)
					s.setNumeroSalle("GSBB");
				else if(i == 9)
					s.setNumeroSalle("I1.1");
				else if(i == 10)
					s.setNumeroSalle("I1.2");
				else
					s.setNumeroSalle("I0." + Integer.toString(i + 1));
				s.setProjecteur(true);
				//s.setDebit(456);
				s.getActifs().addAll(aRepository.findAll().subList(i * 36, ((i + 1) * 36)));
				s.getLogiciels().addAll(lRepository.findAll());
				repository.save(s);
			}
			for(int i = 3;i<10;i++) {
				Salle s = new Salle();
				s.setNumeroSalle("I1." + Integer.toString(i));
				s.setProjecteur(false);
				//s.setDebit(456);
				repository.save(s);
			}
			}
			
			
		return repository.findAll();
	}

	public long count() {
		return repository.count();
	}

	public List<Actif> fetchAllActifs(String numeroSalle) {
		return  repository.findByNumeroSalle(numeroSalle).getActifs();
	}

	public List<Logiciel> fetchAllLogiciels(String numeroSalle) {
		return repository.findByNumeroSalle(numeroSalle).getLogiciels();
	}
	
	public boolean getDataShow(String numeroSalle) {
		return repository.findByNumeroSalle(numeroSalle).isProjecteur();
	}

	public boolean updateDataShow(String numeroSalle, boolean value) {
		Salle salle = repository.findByNumeroSalle(numeroSalle);
		salle.setProjecteur(value);
		repository.save(salle);
		
		return true;
	}
	
	public int fetchNombreTable(String numeroSalle) {
		return repository.findByNumeroSalle(numeroSalle).getNombreTable();
	}
	
	public boolean updateNombreTable(String numeroSalle,int nombre) {
		Salle salle = repository.findByNumeroSalle(numeroSalle);
		if(salle.getNombreTable()+nombre >= 0) {
		salle.setNombreTable(salle.getNombreTable()+nombre);
		repository.save(salle);
		}
		return true;
	}

}
