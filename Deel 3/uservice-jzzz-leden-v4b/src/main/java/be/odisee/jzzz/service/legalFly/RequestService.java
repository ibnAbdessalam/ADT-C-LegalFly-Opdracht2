package be.odisee.jzzz.service.legalFly;

import be.odisee.jzzz.domain.legalFly.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    // Query methods
    List<Request> getAllRequests();

    Optional<Request> getRequestById(Long id);

    List<Request> getRequestsByStatus(String status);

    // Command methods
    Request createRequest(Request request);

    Optional<Request> updateRequest(Long id, Request updatedRequest);

    boolean deleteRequest(Long id);
}
