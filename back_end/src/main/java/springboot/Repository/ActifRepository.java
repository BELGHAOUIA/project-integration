package springboot.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Sort;
import springboot.Entity.Actif;
import springboot.Entity.Salle;
@Repository
public interface ActifRepository extends JpaRepository<Actif, Long>{
	@Query("SELECT a FROM Actif a JOIN FETCH a.salle s WHERE s.id = :salleId")
	List<Actif> fetchAllBySalleId(@Param("salleId") long salleId);
	
	List<Actif> findBySalle(Salle salle);
	
	Actif findByReference(String reference);

	Actif findByNumeroActif(int numeroActif);

	/*@Query("SELECT numeroActif FROM Actif a JOIN FETCH a.salle s WHERE typeActif = 'Pc' AND s.numeroSalle = :numeroSalle")
	List<Integer> fetchAllNumeroActifOfPcByNumeroSalle(@Param("numeroSalle") String numeroSalle);*/
}
