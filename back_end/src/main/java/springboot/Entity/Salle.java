package springboot.Entity;

import java.io.Serializable;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Data
//@OnDelete(action = OnDeleteAction.CASCADE)
public class Salle implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false, updatable = false)
	private String numeroSalle;
	
	@Column(nullable = false, updatable = false)
	private int nombrePoste ;
	
	@Column(nullable = false)
	private boolean projecteur;
	
	@Column(nullable = false, updatable = false)
	private float debit = (float) Math.random()*1000;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "salle_logiciels", 
	  joinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id"),
	  inverseJoinColumns = @JoinColumn(name = "logiciel_id", referencedColumnName = "id"))
	private List<Logiciel> logiciels = new LinkedList<>();

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "salle_id")
	@JsonManagedReference("listeActif")
	private List<Actif> actifs = new LinkedList<>();

	private int nombreTable = 4;

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "salle_id")
	@JsonManagedReference("listeEmlpoi")
	private List<EmploiDeTemps> emploiDeTemps = new LinkedList<>();

}
