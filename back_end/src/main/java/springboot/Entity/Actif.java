package springboot.Entity;

import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springboot.Entity.Enum.EtatActif;
import springboot.Entity.Enum.TypeActif;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Data
//@OnDelete(action = OnDeleteAction.CASCADE)
public class Actif implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String reference = UUID.randomUUID().toString().substring(0, 8);
	
	private String modele;
	
	@Enumerated(EnumType.STRING)
	private TypeActif type;

	@Enumerated(EnumType.STRING)
	private EtatActif etat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference("listeActif")
	//@JsonDeserialize
	private Salle salle;

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "actif_id")
	@JsonManagedReference("listeTicketActif")
	private List<Ticket> tickets = new LinkedList<>();

	private int numeroActif;

}
