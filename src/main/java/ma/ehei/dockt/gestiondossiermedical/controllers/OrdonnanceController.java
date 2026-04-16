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

    // URL : GET http://localhost:8081/api/ordonnances/rdv/1
    @GetMapping("/rdv/{idRdv}")
    public List<Ordonnance> getOrdonnances(@PathVariable Long idRdv) {
        return service.getOrdonnancesParRdv(idRdv);
    }

    // URL : POST http://localhost:8081/api/ordonnances
    @PostMapping
    public Ordonnance sauvegarderOrdonnance(@RequestBody Ordonnance ordonnance) {
        return service.sauvegarderOrdonnance(ordonnance);
    }

    // URL : DELETE http://localhost:8081/api/ordonnances/1
    @DeleteMapping("/{id}")
    public void supprimerOrdonnance(@PathVariable Long id) {
        service.supprimerOrdonnance(id);
    }
}