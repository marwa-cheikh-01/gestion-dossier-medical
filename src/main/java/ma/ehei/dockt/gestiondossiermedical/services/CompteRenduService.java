package ma.ehei.dockt.gestiondossiermedical.services;

import ma.ehei.dockt.gestiondossiermedical.models.CompteRendu;
import ma.ehei.dockt.gestiondossiermedical.repositories.CompteRenduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ma.ehei.dockt.gestiondossiermedical.dto.RdvDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CompteRenduService {

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    @Autowired
    private RdvClientService rdvClientService;

    // 1. Récupérer tous les CRs pour un RDV
    public List<CompteRendu> getComptesRendusParRdv(Long idRdv) {
        return compteRenduRepository.findByIdRdv(idRdv);
    }

    // 2. Récupérer un CR par son ID
    public CompteRendu getCompteRenduById(Long id) {
        return compteRenduRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Compte rendu avec l'ID " + id + " introuvable."
                ));
    }

    // 3. Récupérer les CR par id patient
    public List<CompteRendu> getComptesRendusParPatient(Long patientId, String bearerToken) {
        List<RdvDTO> rdvs = rdvClientService.getRdvsByPatient(patientId, bearerToken);
        List<Long> rdvIds = rdvs.stream()
                .map(RdvDTO::getId)
                .collect(java.util.stream.Collectors.toList());
        if (rdvIds.isEmpty()) return List.of();
        return compteRenduRepository.findByIdRdvIn(rdvIds);
    }

    // 4. Patient demande un CR (statut = DEMANDE)
    public CompteRendu demanderCompteRendu(CompteRendu cr, String bearerToken) {
        validerRdvExiste(cr.getIdRdv(), bearerToken);
        cr.setStatut("DEMANDE");
        cr.setDateRedaction(LocalDate.now());
        return compteRenduRepository.save(cr);
    }

    // 5. Médecin sauvegarde brouillon (statut = EN_ATTENTE)
    public CompteRendu sauvegarderBrouillon(CompteRendu cr, String bearerToken) {
        validerRdvExiste(cr.getIdRdv(), bearerToken);
        cr.setStatut("EN_ATTENTE");
        cr.setDateRedaction(LocalDate.now());
        return compteRenduRepository.save(cr);
    }

    // 6. Médecin valide le CR (statut = VALIDE)
    public CompteRendu validerCompteRendu(CompteRendu cr, String bearerToken) {
        validerRdvExiste(cr.getIdRdv(), bearerToken);
        cr.setStatut("VALIDE");
        cr.setDateRedaction(LocalDate.now());
        return compteRenduRepository.save(cr);
    }

    // 7. Médecin met à jour un CR existant (DEMANDE → EN_ATTENTE)
    public CompteRendu mettreAJourBrouillon(Long id, CompteRendu cr) {
        CompteRendu existing = getCompteRenduById(id);
        existing.setContenu(cr.getContenu());
        existing.setStatut("EN_ATTENTE");
        existing.setDateRedaction(LocalDate.now());
        // messagePatient untouched ✅
        return compteRenduRepository.save(existing);
    }

    // 8. Médecin valide un CR existant (EN_ATTENTE ou DEMANDE → VALIDE)
    public CompteRendu validerCompteRenduById(Long id, CompteRendu cr) {
        CompteRendu existing = getCompteRenduById(id);
        existing.setContenu(cr.getContenu());
        existing.setStatut("VALIDE");
        existing.setDateRedaction(LocalDate.now());
        // messagePatient untouched ✅
        return compteRenduRepository.save(existing);
    }

    // 9. Récupérer les CR par statut
    public List<CompteRendu> getComptesRendusParStatut(String statut) {
        return compteRenduRepository.findByStatut(statut);
    }

    // 10. helper to avoid repeating
    private void validerRdvExiste(Long idRdv, String bearerToken) {
        RdvDTO rdv = rdvClientService.getRdvById(idRdv, bearerToken);
        if (rdv == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "RDV avec l'ID " + idRdv + " introuvable dans MS2."
            );
        }
    }
}