package ma.ehei.dockt.gestiondossiermedical.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "compte_rendu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteRendu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cr")
    private Long idCr;

    @Column(name = "date_redaction", nullable = false)
    private LocalDate dateRedaction;

    @Column(name = "contenu", nullable = false, length = 500)
    private String contenu;

    @Column(name = "statut", nullable = false, length = 150)
    private String statut;

    @Column(name = "id_rdv", nullable = false)
    private Long idRdv;
}