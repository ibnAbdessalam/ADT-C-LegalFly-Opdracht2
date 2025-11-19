package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.dao.RequestRepository;
import be.odisee.brainstorm.domain.LegalFly.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("legalFlyService")
public class LegalFlyServiceImpl implements LegalFlyService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request getRequestById(int id) {
        return requestRepository.findById(id).orElse(null);
    }
}