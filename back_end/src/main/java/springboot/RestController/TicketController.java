package springboot.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entity.Enum.Status;
import springboot.Entity.Ticket;
import springboot.RestController.impl.TicketControllerImpl;
import springboot.Service.TicketService;
import springboot.customRequests.CustomTicketRequest;
import springboot.customResponses.CustomTicketResponse;

@CrossOrigin
@RestController
public class TicketController implements TicketControllerImpl {
	
	TicketService service;
	
	@Autowired
	public TicketController(TicketService service) {
		this.service = service;
	}
	
	@Override
	public boolean add(CustomTicketRequest a, UserDetails currentUser) {
		return service.add(a,currentUser.getUsername());
	}

	@Override
	public boolean delete(long id) {
		return service.delete(id);
	}
	
	@Override
	public boolean update(long id, Status status, UserDetails userDetails) {
		return service.update(id,status, userDetails.getUsername());
	}

	@Override
	public List<CustomTicketResponse> fetchPerte() {
		return service.fetchPerte();
	}

	@Override
	public List<CustomTicketResponse> fetchProbleme() {
		return service.fetchProbleme();
	}

	@Override
	public List<CustomTicketResponse> fetchTicketEnAttent() {
		return service.fetchTicketEnAttent();
	}

	@Override
	public List<CustomTicketResponse> fetchTicketsByUser(UserDetails userDetails) {
		return service.fetchTicketsByUser(userDetails.getUsername());
	}

    @Override
    public List<CustomTicketResponse> fetchAcceptedTickets() {
        return service.fetchAcceptedTickets();
    }


}
