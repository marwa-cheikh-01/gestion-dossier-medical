package ma.ehei.dockt.gestiondossiermedical.controllers;

import ma.ehei.dockt.gestiondossiermedical.models.CompteRendu;
import ma.ehei.dockt.gestiondossiermedical.services.CompteRenduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes-rendus")
@CrossOrigin(origins = "*")
public class CompteRenduController {

    @Autowired
    private CompteRenduService service;

    // GET http://localhost:8080/api/comptes-rendus/rdv/1
    @GetMapping("/rdv/{idRdv}")
    public List<CompteRendu> getComptesRendus(@PathVariable Long idRdv) {
        return service.getComptesRendusParRdv(idRdv);
    }

    // GET http://localhost:8080/api/comptes-rendus/1
    @GetMapping("/{id}")
    public CompteRendu getCompteRenduById(@PathVariable Long id) {
        return service.getCompteRenduById(id);
    }

    // GET all CR by patient id
    @GetMapping("/patient/{patientId}")
    public List<CompteRendu> getComptesRendusParPatient(
            @PathVariable Long patientId,
            @RequestHeader("Authorization") String bearerToken) {
        return service.getComptesRendusParPatient(patientId, bearerToken);
    }

    // POST http://localhost:8080/api/comptes-rendus/demande
    @PostMapping("/demande")
    public CompteRendu demanderCompteRendu(
            @RequestBody CompteRendu cr,
            @RequestHeader("Authorization") String bearerToken) {
        return service.demanderCompteRendu(cr, bearerToken);
    }

    // POST http://localhost:8080/api/comptes-rendus/brouillon
    @PostMapping("/brouillon")
    public CompteRendu sauvegarderBrouillon(
            @RequestBody CompteRendu cr,
            @RequestHeader("Authorization") String bearerToken) {
        return service.sauvegarderBrouillon(cr, bearerToken);
    }

    // POST http://localhost:8080/api/comptes-rendus/valider
    @PostMapping("/valider")
    public CompteRendu validerCompteRendu(
            @RequestBody CompteRendu cr,
            @RequestHeader("Authorization") String bearerToken) {
        return service.validerCompteRendu(cr, bearerToken);
    }

    @PutMapping("/brouillon/{id}")
    public CompteRendu mettreAJourBrouillon(
            @PathVariable Long id,
            @RequestBody CompteRendu cr) {
        return service.mettreAJourBrouillon(id, cr);
    }

    @PutMapping("/valider/{id}")
    public CompteRendu validerCompteRendu(
            @PathVariable Long id,
            @RequestBody CompteRendu cr) {
        return service.validerCompteRenduById(id, cr);
    }

    // GET all CRs by statut — for médecin dashboard
    @GetMapping("/statut/{statut}")
    public List<CompteRendu> getComptesRendusParStatut(@PathVariable String statut) {
        return service.getComptesRendusParStatut(statut);
    }
}