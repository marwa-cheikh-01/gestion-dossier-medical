package ma.ehei.dockt.gestiondossiermedical.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RdvDTO {
    private Long id;
    private Long idPatient;
    private String statutRdv;
    private String statutConsultation;
    private LocalDate datePrevue;
}