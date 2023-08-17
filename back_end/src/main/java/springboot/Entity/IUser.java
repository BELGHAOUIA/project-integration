package springboot.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import springboot.Entity.Enum.Role;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	@Column(unique = true)
	private String email;
	private String password;

	@Lob
	private byte[] profilePicture;
    private boolean isActive;

	@ElementCollection(fetch= FetchType.EAGER)
	@CollectionTable(
			name="roles",
			joinColumns = @JoinColumn(name="user_id")
			)
	@Column(name="user_role")
	private List<Role> roles;

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "user_id")
	@JsonManagedReference("userTickets")
	private List<Ticket> tickets;

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "user_id")
	@JsonManagedReference("userNotifications")
	private List<Notification> notifications;

	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "user_id")
	@JsonManagedReference("userTokens")
	private List<VerificationToken> verificationTokens;
}

