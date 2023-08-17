package springboot.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.description.annotation.AnnotationValue.Sort;
import springboot.Entity.Actif;
import springboot.Entity.Salle;
import springboot.Entity.Enum.BrandNames;
import springboot.Entity.Enum.EtatActif;
import springboot.Entity.Enum.TypeActif;
import springboot.Repository.ActifRepository;
import springboot.Repository.SalleRepository;
import springboot.customResponses.CustomActifResponse;

@Service
public class ActifService {

	ActifRepository repository;

	SalleRepository sRepository;

	@Autowired
	public ActifService(ActifRepository actifRepository, SalleRepository sRepository) {
		this.repository = actifRepository;
		this.sRepository = sRepository;
	}

	public Actif add(Actif a) {
		return repository.save(a);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void update(long id, Actif a) {
		a.setId(id);
		repository.save(a);
	}

	public void updateEtatActif(long id, EtatActif etatActif) {
		Actif a =repository.getReferenceById(id);
		a.setEtat(etatActif);
		repository.save(a);
	}

	public void start() {
		int i,j;
		String brandName = "";

		for(i = 0;i < 11; i++) {
			if(i == 0)
				brandName = BrandNames.Acer.toString();

			if(i == 1)
				brandName = BrandNames.Apple.toString();

			if(i == 2)
				brandName = BrandNames.Asus.toString();

			if(i == 3)
				brandName = BrandNames.Dell.toString();

			if(i == 4)
				brandName = BrandNames.HP.toString();

			if(i == 5)
				brandName = BrandNames.Lenovo.toString();

			if(i == 6)
				brandName = BrandNames.MSI.toString();

			if(i == 7)
				brandName = BrandNames.Asus.toString();

			if(i == 8)
				brandName = BrandNames.MSI.toString();

			if(i == 9)
				brandName = BrandNames.HP.toString();

			if(i == 10)
				brandName = BrandNames.Lenovo.toString();

			for(j = 0;j < 9; j++) {
				Actif a = new Actif();
				a.setEtat(EtatActif.NORMALE);
				a.setModele(brandName);
				a.setType(TypeActif.Pc);
				a.setNumeroActif(j+1);
				repository.save(a);
			}

			for(j = 0;j < 9; j++) {
				Actif a = new Actif();
				a.setEtat(EtatActif.NORMALE);
				a.setModele(brandName);
				a.setType(TypeActif.Unite);
				a.setNumeroActif(j);
				repository.save(a);
			}

			for(j = 0;j < 9; j++) {
				Actif a = new Actif();
				a.setEtat(EtatActif.NORMALE);
				a.setModele(brandName);
				a.setType(TypeActif.Ecran);
				a.setNumeroActif(j);
				repository.save(a);
			}

			for(j = 0;j < 9; j++) {
				Actif a = new Actif();
				a.setEtat(EtatActif.NORMALE);
				a.setModele(brandName);
				a.setType(TypeActif.Souris);
				a.setNumeroActif(j);
				repository.save(a);
			}
		}
	}

	public List<Actif> fetchAllBySalleId(long id) {
		//return repository.findBySalle(sRepository.getReferenceById(id));
		return repository.fetchAllBySalleId(id);
	}

    public List<CustomActifResponse> fetchAll() {
		return customActifFetcher(repository.findAll());
    }

	public Actif findByNumeroActif(int numeroActif) {
		return repository.findByNumeroActif(numeroActif);
	}
	public List<CustomActifResponse> customActifFetcher(List<Actif> actifs) {
		List<CustomActifResponse> customActifResponses = new LinkedList<>();
		CustomActifResponse ac ;
		String numeroSalle = null;
		for (Actif actif: actifs) {
			if(actif.getSalle() == null) numeroSalle = "";
			else numeroSalle = actif.getSalle().getNumeroSalle();
			ac = new CustomActifResponse(
					actif.getId(),
					actif.getReference(),
					actif.getModele(),
					actif.getType().toString(),
					actif.getEtat().getName(),
					numeroSalle);
			customActifResponses.add(ac);
		}
		return customActifResponses;
	}

}
