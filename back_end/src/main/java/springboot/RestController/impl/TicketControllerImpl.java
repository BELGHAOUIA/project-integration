package springboot.RestController.impl;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import springboot.Entity.Enum.Status;
import springboot.customRequests.CustomTicketRequest;
import springboot.customResponses.CustomTicketResponse;

@RequestMapping("/ticket/")
public interface TicketControllerImpl {

	@PostMapping("add")
	public boolean add(@RequestBody CustomTicketRequest a, @AuthenticationPrincipal UserDetails currentUser);

	@DeleteMapping("delete")
	public boolean delete(@RequestParam(name = "id") long id);
	
	@PutMapping("status/update")
	public boolean update(@RequestParam(name="id") long id,@RequestParam(name = "status") Status status,
						  @AuthenticationPrincipal UserDetails userDetails);
	
	@GetMapping("perte/fetchAll")
	public List<CustomTicketResponse> fetchPerte();

	@GetMapping("probleme/fetchAll")
	public List<CustomTicketResponse> fetchProbleme();

	@GetMapping("ticketEnAttent/fetchAll")
	public List<CustomTicketResponse> fetchTicketEnAttent();

	@GetMapping("ticketByUser/fetchAll")
	public List<CustomTicketResponse> fetchTicketsByUser(@AuthenticationPrincipal UserDetails userDetails);

	@GetMapping("acceptedTickets/fetchAll")
	public List<CustomTicketResponse> fetchAcceptedTickets();
}
