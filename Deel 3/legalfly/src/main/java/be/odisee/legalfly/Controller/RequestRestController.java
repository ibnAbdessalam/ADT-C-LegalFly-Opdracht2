package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Request;
import be.odisee.legalfly.Dto.CreateRequestRequest;
import be.odisee.legalfly.Service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestRestController {
    private final RequestService requestService;

    public RequestRestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<Request> getAll() {
        return requestService.getAllRequests();
    }

    @PostMapping
    public void createRequest(@RequestBody CreateRequestRequest request) {
        requestService.createRequest(request.juristId, request.lawId, request.content);
    }
}
