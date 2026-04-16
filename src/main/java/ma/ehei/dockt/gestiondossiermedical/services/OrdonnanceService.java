package ma.ehei.dockt.gestiondossiermedical.services;

import ma.ehei.dockt.gestiondossiermedical.models.Ordonnance;
import ma.ehei.dockt.gestiondossiermedical.repositories.OrdonnanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrdonnanceService {

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    // 1. Récupérer toutes les ordonnances pour un RDV précis (pour l'historique)
    public List<Ordonnance> getOrdonnancesParRdv(Long idRdv) {
        return ordonnanceRepository.findByIdRdv(idRdv);
    }

    // 2. Sauvegarder une nouvelle ordonnance (dictée ou écrite à la main)
    public Ordonnance sauvegarderOrdonnance(Ordonnance ordonnance) {
        // On s'assure que la date est bien celle d'aujourd'hui
        ordonnance.setDateEmmission(LocalDate.now());

        // On sauvegarde dans la base de données
        return ordonnanceRepository.save(ordonnance);
    }

    // 3. (Bonus) Supprimer une ordonnance si le médecin s'est trompé
    public void supprimerOrdonnance(Long id) {
        // 1. On vérifie d'abord si l'ordonnance existe
        if (!ordonnanceRepository.existsById(id)) {
            // 2. Si elle n'existe pas, on déclenche une Erreur 404 avec un message clair
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Impossible de supprimer : l'ordonnance avec l'ID " + id + " n'existe pas."
            );
        }

        // 3. Si elle existe, on procède à la suppression
        ordonnanceRepository.deleteById(id);
    }}