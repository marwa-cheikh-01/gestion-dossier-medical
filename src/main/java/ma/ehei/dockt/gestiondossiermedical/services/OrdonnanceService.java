package ma.ehei.dockt.gestiondossiermedical.services;


import ma.ehei.dockt.gestiondossiermedical.models.Ordonnance;
import ma.ehei.dockt.gestiondossiermedical.repositories.OrdonnanceRepository;
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
public class OrdonnanceService {

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    @Autowired
    private RdvClientService rdvClientService;

    // 1. Récupérer toutes les ordonnances pour un RDV
    public List<Ordonnance> getOrdonnancesParRdv(Long idRdv) {
        return ordonnanceRepository.findByIdRdv(idRdv);
    }

    // 2. Récupérer une ordonnance par son ID
    public Ordonnance getOrdonnanceById(Long id) {
        return ordonnanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ordonnance avec l'ID " + id + " introuvable."
                ));
    }

    //3. Récupérer les ord by id patient
    public List<Ordonnance> getOrdonnancesParPatient(Long patientId) {
        List<RdvDTO> rdvs = rdvClientService.getRdvsByPatient(patientId);
        List<Long> rdvIds = rdvs.stream()
                .map(RdvDTO::getId)
                .collect(java.util.stream.Collectors.toList());
        if (rdvIds.isEmpty()) return List.of();
        return ordonnanceRepository.findByIdRdvIn(rdvIds);
    }

    // 4. Sauvegarder une ordonnance
    public Ordonnance sauvegarderOrdonnance(Ordonnance ordonnance) {
        // Validate RDV exists in MS2
        RdvDTO rdv = rdvClientService.getRdvById(ordonnance.getIdRdv());
        if (rdv == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "RDV avec l'ID " + ordonnance.getIdRdv() + " introuvable dans MS2."
            );
        }
        ordonnance.setDateEmmission(LocalDate.now());
        return ordonnanceRepository.save(ordonnance);
    }



}