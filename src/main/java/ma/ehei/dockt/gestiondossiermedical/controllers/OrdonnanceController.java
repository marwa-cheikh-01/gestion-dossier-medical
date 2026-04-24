package ma.ehei.dockt.gestiondossiermedical.controllers;

import ma.ehei.dockt.gestiondossiermedical.models.Ordonnance;
import ma.ehei.dockt.gestiondossiermedical.services.OrdonnanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordonnances")
@CrossOrigin(origins = "*")
public class OrdonnanceController {

    @Autowired
    private OrdonnanceService service;

    // GET http://localhost:8080/api/ordonnances/rdv/1
    //hadi dyal marwa jibi ord by idrdv dont need it nkhliwha tanchofo mn bead
    @GetMapping("/rdv/{idRdv}")
    public List<Ordonnance> getOrdonnances(@PathVariable Long idRdv) {
        return service.getOrdonnancesParRdv(idRdv);
    }

    //get all ord by patient
    @GetMapping("/patient/{patientId}")
    public List<Ordonnance> getOrdonnancesParPatient(@PathVariable Long patientId) {
        return service.getOrdonnancesParPatient(patientId);
    }

    // GET http://localhost:8080/api/ordonnances/1
    //voir single ord
    @GetMapping("/{id}")
    public Ordonnance getOrdonnanceById(@PathVariable Long id) {
        return service.getOrdonnanceById(id);
    }

    // POST http://localhost:8080/api/ordonnances
    //valider cree ard
    @PostMapping
    public Ordonnance sauvegarderOrdonnance(@RequestBody Ordonnance ordonnance) {
        return service.sauvegarderOrdonnance(ordonnance);
    }

}