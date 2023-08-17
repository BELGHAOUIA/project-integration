package springboot.customResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import springboot.Entity.Enum.Priority;
import springboot.Entity.Enum.Status;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class CustomTicketResponse {
    private int id;
    private String userName;
    private String email;
    private String details;
    private Date dateOpened;
    private Date dateClosed;
    private Date lastUpdated;
    private String status;
    private String numeroSalle;
    private Integer numeroActif;
    private String priority;
}
