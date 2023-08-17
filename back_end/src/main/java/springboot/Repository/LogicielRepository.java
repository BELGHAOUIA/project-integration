package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.Logiciel;

@Repository
public interface LogicielRepository extends JpaRepository<Logiciel, Long>{
	Logiciel findByNom(String nom);
}
