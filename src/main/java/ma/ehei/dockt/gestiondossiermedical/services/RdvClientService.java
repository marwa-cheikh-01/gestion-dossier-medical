package ma.ehei.dockt.gestiondossiermedical.services;

import ma.ehei.dockt.gestiondossiermedical.dto.RdvDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RdvClientService {

    private final RestTemplate restTemplate;

    @Value("${services.rendezvous.url}")
    private String rdvServiceUrl;

    public RdvClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RdvDTO getRdvById(Long idRdv) {
        try {
            String url = rdvServiceUrl + "/api/rdv/" + idRdv;
            return restTemplate.getForObject(url, RdvDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null; // RDV doesn't exist
        }
    }

    public List<RdvDTO> getRdvsByPatient(Long patientId) {
        try {
            String url = rdvServiceUrl + "/api/rdv/patient/" + patientId;
            RdvDTO[] rdvs = restTemplate.getForObject(url, RdvDTO[].class);
            return rdvs != null ? List.of(rdvs) : List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
}