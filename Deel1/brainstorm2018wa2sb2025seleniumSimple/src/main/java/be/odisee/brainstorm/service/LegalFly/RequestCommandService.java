package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.dao.JuristRepository;
import be.odisee.brainstorm.dao.LawRepository;
import be.odisee.brainstorm.dao.RequestRepository;
import be.odisee.brainstorm.domain.LegalFly.Jurist;
import be.odisee.brainstorm.domain.LegalFly.Law;
import be.odisee.brainstorm.domain.LegalFly.Request;
import be.odisee.brainstorm.dto.CreateRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class RequestCommandService {
    private final RequestRepository requestRepository;
    private final JuristRepository juristRepository;
    private final LawRepository lawRepository;

    public RequestCommandService(RequestRepository requestRepository, JuristRepository juristRepository, LawRepository lawRepository) {
        this.requestRepository = requestRepository;
        this.juristRepository = juristRepository;
        this.lawRepository = lawRepository;
    }

    public Request createRequest(CreateRequestDTO dto) {
        Jurist jurist = juristRepository.findById(dto.juristId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Jurist ID"));
        Law law = lawRepository.findById(dto.lawId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Law ID"));

        Request newRequest = new Request();
        newRequest.setJurist(jurist);
        newRequest.setLaw(law);
        newRequest.setContent(dto.content());
        newRequest.setStatus("pending");

        return requestRepository.save(newRequest);
    }
}