package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.domain.LegalFly.Request;
import java.util.List;

public interface LegalFlyService {
    List<Request> getAllRequests();
    Request getRequestById(int id);
}