package springboot.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.Entity.Enum.Jour;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EmploiDeTemps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Jour jour;

    private String prof1;
    private String prof2;
    private String prof3;
    private String prof4;
    private String prof5;
    private String prof6;

    @ManyToOne(optional = true)
    @JsonBackReference("listeEmlpoi")
    private Salle salle;

}
