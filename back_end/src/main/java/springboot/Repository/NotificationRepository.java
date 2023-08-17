package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.Entity.IUser;
import springboot.Entity.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
   // @Query("SELECT COUNT(n) FROM Notification n JOIN FETCH n.user u WHERE u.id = :user_id")
    int countByUser(IUser user);

    List<Notification> findByUser(IUser user);

   /* @Query("DELETE FROM Notification e WHERE e.user = :user")
    void deleteAllByUser(IUser user);*/
}
