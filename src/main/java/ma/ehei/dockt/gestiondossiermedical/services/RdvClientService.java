package ma.ehei.dockt.gestiondossiermedical.services;

import ma.ehei.dockt.gestiondossiermedical.dto.RdvDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    // ✅ Used when saving ordonnance/CR — no token needed (internal validation)
    public RdvDTO getRdvById(Long idRdv, String bearerToken) {
        try {
            String url = rdvServiceUrl + "/api/rdv/" + idRdv;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<RdvDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, RdvDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    // ✅ Forward the patient's JWT token to MS2
    public List<RdvDTO> getRdvsByPatient(Long patientId, String bearerToken) {
        try {
            String url = rdvServiceUrl + "/api/rdv/patient/" + patientId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken); // forward the token
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<RdvDTO[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, RdvDTO[].class);

            RdvDTO[] rdvs = response.getBody();
            return rdvs != null ? List.of(rdvs) : List.of();
        } catch (Exception e) {
            System.out.println("MS2 call FAILED: " + e.getMessage());
            return List.of();
        }
    }
}