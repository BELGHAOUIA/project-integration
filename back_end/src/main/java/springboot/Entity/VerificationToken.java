package springboot.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdTimestamp;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDate expireAt;

    @Transient
    private boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference("userTokens")
    private IUser user;

    public boolean isExpired() {
        return getExpireAt().isBefore(ChronoLocalDate.from(LocalDateTime.now())); // this is generic implementation, you can always make it timezone specific
    }
}
