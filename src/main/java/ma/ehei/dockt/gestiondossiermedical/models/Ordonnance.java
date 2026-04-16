package ma.ehei.dockt.gestiondossiermedical.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "ordonnance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordonnance")
    private Long idOrdonnance;

    @Column(name = "date_emmission", nullable = false)
    private LocalDate dateEmmission;

    @Column(name = "contenu_texte", nullable = false, length = 300)
    private String contenuTexte;

    @Column(name = "type", nullable = false, length = 150)
    private String type;

    @Column(name = "id_rdv", nullable = false)
    private Long idRdv;
}