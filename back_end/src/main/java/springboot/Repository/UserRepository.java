package springboot.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.IUser;

@Repository
public interface UserRepository extends JpaRepository<IUser, Long>{
	Optional<IUser> findByEmail(String email);
	Optional<IUser> findByUserName(String userName);

	@Query(value = "SELECT DISTINCT i.user_name FROM iuser i, roles r WHERE i.id = r.user_id AND r.user_role = 'PROF'", nativeQuery = true)
	List<String> findUserNamesByRoleProf();

}