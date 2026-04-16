package ma.ehei.dockt.gestiondossiermedical.services;

import ma.ehei.dockt.gestiondossiermedical.models.CompteRendu;
import ma.ehei.dockt.gestiondossiermedical.repositories.CompteRenduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CompteRenduService {

    // On "branche" le Repository pour pouvoir l'utiliser dans le cerveau
    @Autowired
    private CompteRenduRepository compteRenduRepository;

    // 1. Pour afficher l'historique dans Angular
    public List<CompteRendu> getComptesRendusParRdv(Long idRdv) {
        return compteRenduRepository.findByIdRdv(idRdv);
    }

    // 2. Le bouton "Sauvegarder Brouillon"
    public CompteRendu sauvegarderBrouillon(CompteRendu cr) {
        // Règle métier : On force le statut et la date avant d'enregistrer
        cr.setStatut("Brouillon");
        cr.setDateRedaction(LocalDate.now());
        return compteRenduRepository.save(cr);
    }

    // 3. Le bouton "Valider Compte Rendu"
    public CompteRendu validerCompteRendu(CompteRendu cr) {
        // Règle métier : On force le statut sur Validé
        cr.setStatut("Validé");
        cr.setDateRedaction(LocalDate.now());
        return compteRenduRepository.save(cr);
    }
}