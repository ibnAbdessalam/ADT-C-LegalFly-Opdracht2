package be.odisee.legalfly.Service;

import be.odisee.legalfly.Dao.*;
import be.odisee.legalfly.Domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final JuristRepository juristRepository;
    private final LawRepository lawRepository;

    public RequestServiceImpl(RequestRepository requestRepository,
                              JuristRepository juristRepository,
                              LawRepository lawRepository) {
        this.requestRepository = requestRepository;
        this.juristRepository = juristRepository;
        this.lawRepository = lawRepository;
    }

    @Override
    public void createRequest(int juristId, int lawId, String content) {
        Jurist jurist = juristRepository.findById(juristId)
                .orElseThrow(() -> new RuntimeException("Jurist niet gevonden met ID: " + juristId));

        Law law = lawRepository.findById(lawId)
                .orElseThrow(() -> new RuntimeException("Wet niet gevonden met ID: " + lawId));

        Request request = new Request();
        request.setJurist(jurist);
        request.setLaw(law);
        request.setContent(content);
        request.setStatus("pending");
        request.setVersionNumber(1);
        request.setNotificationStatus("todo");
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestsForJurist(int juristId) {
        return requestRepository.findAll()
                .stream()
                .filter(r -> r.getJurist().getId() == juristId)
                .toList();
    }

    public void deleteRequest(int requestId) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request niet gevonden met ID: " + requestId));
        requestRepository.delete(request);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request getRequestById(int requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request niet gevonden met ID: " + requestId));
    }
}