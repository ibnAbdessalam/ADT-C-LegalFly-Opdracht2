package be.odisee.legalfly.Service;

import be.odisee.legalfly.Domain.Request;

import java.util.List;

public interface RequestService {

    void createRequest(int juristId, int lawId, String content);

    List<Request> getRequestsForJurist(int juristId);

    List<Request> getAllRequests();

    Request getRequestById(int requestId);
    void deleteRequest(int requestId);
}
