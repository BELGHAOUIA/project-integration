package springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.Entity.Actif;
import springboot.Entity.Logiciel;
import springboot.Entity.Salle;
import springboot.Entity.Enum.TypeActif;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
	@Query("SELECT COUNT(a) FROM Salle s JOIN s.actifs a WHERE s.id = :salleId AND a.type = 'PC'")
	 int countActifsBySalleId(@Param("salleId") long salleId);
	
	Salle findByNumeroSalle(String numeroSalle);

	@Query("SELECT numeroSalle FROM Salle s")
	List<String> fetchNumeroSalle();

	/*@Query("SELECT logiciels FROM Salle WHERE Salle.id = :salleId")
	List<Logiciel> findAllLogicielsById(@Param("salleId")long salleId);
	
	@Query("SELECT actifs FROM Salle WHERE Salle.id = :salleId")
	List<Actif> findAllActifsById(@Param("salleId")long salleId);*/
}
