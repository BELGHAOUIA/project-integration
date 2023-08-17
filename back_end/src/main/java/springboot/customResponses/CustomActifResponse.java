package springboot.customResponses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import springboot.Entity.Enum.EtatActif;
import springboot.Entity.Enum.TypeActif;
import springboot.Entity.Salle;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomActifResponse {
    private long id;

    private String reference;

    private String modele;

    private String type;

    private String etat;

    private String numeroSalle;
}
