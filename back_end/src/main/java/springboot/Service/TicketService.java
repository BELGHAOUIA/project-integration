package springboot.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.Entity.Actif;
import springboot.Entity.Enum.EtatActif;
import springboot.Entity.Enum.Role;
import springboot.Entity.Enum.Status;
import springboot.Entity.Enum.TypeTicket;
import springboot.Entity.IUser;
import springboot.Entity.Ticket;
import springboot.Repository.TicketRepository;
import springboot.customRequests.CustomTicketRequest;
import springboot.customResponses.CustomTicketResponse;
import springboot.utils.JwtUtil;

@Service
public class TicketService {
	@Autowired
	TicketRepository repository;

	@Autowired
	UserService userService;

	@Autowired
	ActifService actifService;

	@Autowired
	SalleService salleService;

	@Autowired
	NotificationService notificationService;

	public boolean add(CustomTicketRequest ticketRequest, String email) {
		Ticket a = new Ticket();
		a.setUser(userService.findByEmail(email).get());
		/*if(ticketRequest.getTypeTicket().equals(TypeTicket.perte))
		a.setStatus(Status.OPEN);*/
		a.setTypeTicket(ticketRequest.getTypeTicket());
		a.setDetails(ticketRequest.getDetails());

		if(!ticketRequest.getNumeroSalle().equals("")) {
			a.setNumeroSalle(ticketRequest.getNumeroSalle());
			if(ticketRequest.getActifId() != -1)  {
				actifService.updateEtatActif((long) ticketRequest.getActifId(), EtatActif.EN_PANNE);
				a.setActif(actifService.repository.getReferenceById((long) ticketRequest.getActifId()));
			}
		}
		a.setPriority(ticketRequest.getPriority());
		repository.save(a);
		for (IUser tech: userService.repository.findAll() ) {
			if(tech.getRoles().contains(Role.TECHNICIEN)) {
				notificationService.add("Une nouveau ticket a ete ajouter par "
								+ userService.findByEmail(email).get().getUserName(),
						tech);
			}
		}
		return true;
	}

	public boolean delete(long id) {
		repository.deleteById(id);
		return true;
	}

	public boolean update(long id, Status status,String email) {
		Ticket ser = repository.getReferenceById(id);
		if(ser.getActif() != null) {
			if(status.equals(Status.CLOSED)) actifService.updateEtatActif(id,EtatActif.NORMALE);
			if(status.equals(Status.WORKING)) actifService.updateEtatActif(id,EtatActif.EN_COURS_DE_REPARATION);
		}
		ser.setStatus(status);
		repository.save(ser);
		notificationService.add("Une ticket a ete met "+ status.getName() +
						" par " +userService.findByEmail(email).get().getUserName(),
				ser.getUser());
		return true;
	}

	public List<CustomTicketResponse> fetchPerte() {
		return customServiceFetcher(repository.findAllByTypeTicketAndStatus(TypeTicket.perte, Status.NEW));
	}

	public List<CustomTicketResponse> fetchProbleme() {
		List<CustomTicketResponse> sb = new LinkedList<>();
		sb.addAll(customServiceFetcher(repository.findAllByTypeTicketAndStatus(TypeTicket.probelm_technique, Status.NEW)));
		sb.addAll(customServiceFetcher(repository.findAllByTypeTicketAndStatus(TypeTicket.probelm_mecanique, Status.NEW)));
		return sb;
	}

	public List<CustomTicketResponse> fetchTicketEnAttent() {
		return customServiceFetcher(repository.findAllByStatus(Status.NEW));
	}

	public List<CustomTicketResponse> customServiceFetcher(List<Ticket> tickets ) {
		List<CustomTicketResponse> customTicketRespons = new LinkedList<>();
		CustomTicketResponse customTicketResponse = null;
		String userName = null;
		String email = null;

		for (Ticket ticket: tickets) {
			Optional<IUser> user = userService.findByEmail(ticket.getUser().getEmail());
			userName = user.get().getUserName();
			email = user.get().getEmail();
			try {
				customTicketResponse = new CustomTicketResponse(
						(int) ticket.getId(),
						userName,
						email,
						ticket.getDetails(),
						ticket.getDateOpened(),
						ticket.getDateClosed(),
						ticket.getLastUpdated(),
						ticket.getStatus().getName(),
						null,
						null,
						ticket.getPriority().getName()
				);
				if(ticket.getNumeroSalle() != null){
					customTicketResponse.setNumeroSalle(ticket.getNumeroSalle());
					if(ticket.getActif() != null) {
						customTicketResponse.setNumeroActif(ticket.getActif().getNumeroActif());
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			customTicketRespons.add(customTicketResponse);
		}
		return customTicketRespons;
	}

	public List<Ticket> removeNewTickets(List<Ticket> tickets) {
		tickets.removeIf(ticket -> ticket.getStatus() == Status.NEW);
		return tickets;
	}

	public List<Ticket> removeClosedTickets(List<Ticket> tickets) {
		tickets.removeIf(ticket -> ticket.getStatus() == Status.CLOSED);
		return tickets;
	}

	public Ticket findById(long id) {
		return repository.getReferenceById(id);
	}

    public List<CustomTicketResponse> fetchTicketsByUser(String email) {
		return customServiceFetcher(repository.findAllByUser(userService.findByEmail(email).get()));
    }

	public List<CustomTicketResponse> fetchAcceptedTickets() {
		return customServiceFetcher(removeClosedTickets(removeNewTickets(repository.findAll())));
	}
}
