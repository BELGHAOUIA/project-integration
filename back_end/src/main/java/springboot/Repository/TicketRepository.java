package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.Entity.Enum.Status;
import springboot.Entity.Enum.TypeTicket;
import springboot.Entity.IUser;
import springboot.Entity.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByTypeTicket(TypeTicket TypeTicket);

    @Query("SELECT t From Ticket t WHERE typeTicket= :typeTicket AND status= :status")
    List<Ticket> findAllByTypeTicketAndStatus(@Param("typeTicket") TypeTicket typeTicket,
                                              @Param("status") Status status);

    List<Ticket> findAllByStatus(Status status);

    List<Ticket> findAllByUser(IUser user);

    /*@Query("SELECT logiciels FROM Salle WHERE Salle.id = :salleId")
	List<Logiciel> findAllLogicielsById(@Param("salleId")long salleId);*/
}
