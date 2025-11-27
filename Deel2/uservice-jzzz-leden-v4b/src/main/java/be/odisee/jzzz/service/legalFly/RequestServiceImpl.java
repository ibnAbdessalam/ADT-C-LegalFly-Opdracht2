package be.odisee.jzzz.service.legalFly;


import be.odisee.jzzz.dao.legalFly.RequestRepository;
import be.odisee.jzzz.domain.legalFly.Request;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    // Query methods
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Optional<Request> getRequestById(Long id) {
        return requestRepository.findById(id);
    }

    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    // Command methods
    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Optional<Request> updateRequest(Long id, Request updatedRequest) {
        return requestRepository.findById(id)
                .map(existingRequest -> {
                    existingRequest.setTitle(updatedRequest.getTitle());
                    existingRequest.setDescription(updatedRequest.getDescription());
                    existingRequest.setStatus(updatedRequest.getStatus());
                    existingRequest.setClientEmail(updatedRequest.getClientEmail());
                    return requestRepository.save(existingRequest);
                });
    }

    public boolean deleteRequest(Long id) {
        if (requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}