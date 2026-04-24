package ma.ehei.dockt.gestiondossiermedical.repositories;

import ma.ehei.dockt.gestiondossiermedical.models.CompteRendu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Long> {

    // C'est ici la magie ! En écrivant juste le nom de cette méthode,
    // Spring comprend qu'il doit chercher les comptes rendus par ID de RDV.
    List<CompteRendu> findByIdRdv(Long idRdv);

    List<CompteRendu> findByIdRdvIn(List<Long> idRdvs);
}