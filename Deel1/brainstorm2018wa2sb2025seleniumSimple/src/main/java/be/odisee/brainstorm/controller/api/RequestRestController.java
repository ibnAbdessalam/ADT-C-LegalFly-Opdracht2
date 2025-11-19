package be.odisee.brainstorm.controller.api;

import be.odisee.brainstorm.dto.RequestDetail;
import be.odisee.brainstorm.dto.RequestSummary;
import be.odisee.brainstorm.service.LegalFly.RequestQueryService;
import org.springframework.web.bind.annotation.*;
import be.odisee.brainstorm.dto.CreateRequestDTO;
import be.odisee.brainstorm.service.LegalFly.RequestCommandService;
import be.odisee.brainstorm.domain.LegalFly.Request;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin
public class RequestRestController {
    private final RequestQueryService queryService;
    private final RequestCommandService commandService;

    public RequestRestController(RequestQueryService queryService, RequestCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping

    public List<RequestSummary> list() {

        return queryService.list();

    }

    @GetMapping("/{id}")

    public RequestDetail detail(@PathVariable Integer id) {

        return queryService.detail(id);

    }

    @PostMapping
    public Request create(@RequestBody CreateRequestDTO dto) {
        return commandService.createRequest(dto);
    }
}
