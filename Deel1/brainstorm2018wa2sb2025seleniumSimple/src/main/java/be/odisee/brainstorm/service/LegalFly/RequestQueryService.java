package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.dao.RequestRepository;
import be.odisee.brainstorm.domain.LegalFly.Request;
import be.odisee.brainstorm.dto.RequestDetail;
import be.odisee.brainstorm.dto.RequestSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestQueryService {

    private final RequestRepository requestRepository;

    public RequestQueryService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<RequestSummary> list() {
        return requestRepository.findAll().stream()
                .map(r -> new RequestSummary(r.getId(), "Contract " + r.getId(), r.getStatus()))
                .toList();
    }

    public RequestDetail detail(Integer id) {
        Request r = requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request niet gevonden: " + id));

        String clientName  = (r.getJurist() != null)
                ? r.getJurist().getFirstName() + " " + r.getJurist().getLastName()
                : "";
        String clientEmail = (r.getJurist() != null) ? r.getJurist().getEmail() : "";
        String preview = r.getContent();

        String lawName = (r.getLaw() != null) ? r.getLaw().getName() : "N/A";
        String lawDescription = (r.getLaw() != null) ? r.getLaw().getDescription() : "No description available.";

        return new RequestDetail(r.getId(), "Contract " + r.getId(), r.getStatus(),
                clientName, clientEmail, preview, lawName, lawDescription);
    }
}
