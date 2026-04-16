package ma.ehei.dockt.gestiondossiermedical.controllers;

import ma.ehei.dockt.gestiondossiermedical.models.CompteRendu;
import ma.ehei.dockt.gestiondossiermedical.services.CompteRenduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes-rendus")
@CrossOrigin(origins = "*") // ⚠️ TRÈS IMPORTANT : Autorise Angular (qui est sur le port 4200) à discuter avec Spring (sur le port 8081)
public class CompteRenduController {

    @Autowired
    private CompteRenduService service;

    // URL : GET http://localhost:8081/api/comptes-rendus/rdv/1
    @GetMapping("/rdv/{idRdv}")
    public List<CompteRendu> getComptesRendus(@PathVariable Long idRdv) {
        return service.getComptesRendusParRdv(idRdv);
    }

    // URL : POST http://localhost:8081/api/comptes-rendus/brouillon
    @PostMapping("/brouillon")
    public CompteRendu sauvegarderBrouillon(@RequestBody CompteRendu cr) {
        return service.sauvegarderBrouillon(cr);
    }

    // URL : POST http://localhost:8081/api/comptes-rendus/valider
    @PostMapping("/valider")
    public CompteRendu validerCompteRendu(@RequestBody CompteRendu cr) {
        return service.validerCompteRendu(cr);
    }
}