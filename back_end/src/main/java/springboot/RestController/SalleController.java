package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import springboot.Entity.Actif;
import springboot.Entity.Logiciel;
import springboot.Entity.Salle;
import springboot.RestController.impl.SalleControllerImpl;
import springboot.Service.SalleActifsService;
import springboot.Service.SalleService;
import springboot.Service.SallesLogicielsService;

@CrossOrigin
@RestController
public class SalleController implements SalleControllerImpl {

	SalleService service;

	SallesLogicielsService sallesLogicielsService;

	SalleActifsService salleActifsService;

	@Autowired
	public SalleController(SalleService service, SallesLogicielsService sallesLogicielsService, SalleActifsService salleActifsService) {
		this.service = service;
		this.sallesLogicielsService = sallesLogicielsService;
		this.salleActifsService = salleActifsService;
	}

	@Override
	public boolean starter() throws RuntimeException{
		try {
			if(service.count() == 0) {
				service.start();
			}
		}
		catch (RuntimeException e) {
			System.out.println(e.getMessage()
					+ "**********************" +
					"ERROR ERROR ERROR STARTER"
					+ "***********************");
		}
			return true;
		}

		@Override
		public Salle add(Salle a) {
			return service.add(a);
		}

		@Override
		public void delete(long id) {
			service.delete(id);
		}

		@Override
		public boolean update(long id, Salle a) {
			service.update(id,a);
			return true;
		}

		@Override
		public List<Salle> fetchAll() {
			service.countPost();
			return service.fetchAll();
		}

		@Override
		public boolean getDataShow(String numeroSalle) {
			return service.getDataShow(numeroSalle);
		}

		@Override
		public boolean updateDataShow(String numeroSalle, boolean value) {
			return service.updateDataShow(numeroSalle,value);
		}

		@Override
		public List<Actif> fetchAllActifs(String numeroSalle) {
			return service.fetchAllActifs(numeroSalle);
		}

		@Override
		public List<Logiciel> fetchAllLogiciels(String numeroSalle) {
			return service.fetchAllLogiciels(numeroSalle);
		}

		@Override
		public boolean addLogiciel(String numeroSalle, Logiciel logiciel) {
			sallesLogicielsService.addLogicielToSalle(numeroSalle, logiciel);
			return true;
		}

		@Override
		public boolean addActif(String numeroSalle, Actif actif) {
			try {
				System.out.println(actif.getNumeroActif());
				salleActifsService.addActifInSalle(numeroSalle, actif);
			} catch (Exception e) {
				throw new RuntimeException("*********************probleme avec numeroActif*******************");
			}
			return true;
		}

		@Override
		public boolean deleteActif(String numeroSalle,long actifId) {
			salleActifsService.deleteActif(numeroSalle, actifId);
			return true;
		}

		@Override
		public boolean deletelogiciel(String numeroSalle, long logicielId) {
			return sallesLogicielsService.deleteLogiciel(numeroSalle,logicielId);
		}

		@Override
		public int fetchNombreTable(String numeroSalle) {
			return service.fetchNombreTable(numeroSalle);
		}

		@Override
		public boolean updateNombreTable(String numeroSalle, int nombre) {
			return service.updateNombreTable(numeroSalle, nombre);
		}

}
