package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.Entity.EmploiDeTemps;
import springboot.Entity.Enum.Jour;

import java.util.List;

@Repository
public interface EmploiDeTempsRepository extends JpaRepository<EmploiDeTemps,Long> {
    EmploiDeTemps findByJour(Jour jour);

    @Query("SELECT a FROM EmploiDeTemps a JOIN FETCH a.salle s WHERE s.numeroSalle = :numeroSalle")
    List<EmploiDeTemps> fetchAllBySalleId(@Param("numeroSalle") String numeroSalle);

}
