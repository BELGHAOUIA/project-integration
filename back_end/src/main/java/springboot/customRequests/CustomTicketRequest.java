package springboot.customRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springboot.Entity.Enum.Priority;
import springboot.Entity.Enum.TypeTicket;

@AllArgsConstructor
@Getter
public class CustomTicketRequest {
    private TypeTicket typeTicket;
    private String details;
    private String numeroSalle;
    private int actifId;
    private Priority priority;
}
