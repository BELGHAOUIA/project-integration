package springboot.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import springboot.Entity.Enum.Priority;
import springboot.Entity.Enum.Status;
import springboot.Entity.Enum.TypeTicket;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private TypeTicket typeTicket;

	@NotNull
	private String details;

//	@CreationTimestamp
//	@Column(updatable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dateOpened ;

	private final Timestamp dateOpened = Timestamp.from(Instant.now());

	private Date dateClosed;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference("userTickets")
	private IUser user;

	@Enumerated(EnumType.STRING)
	private Status status = Status.NEW;

	private String numeroSalle;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JsonBackReference("listeTicketActif")
	private Actif actif;

	@Enumerated(EnumType.STRING)
	private Priority priority;
}
