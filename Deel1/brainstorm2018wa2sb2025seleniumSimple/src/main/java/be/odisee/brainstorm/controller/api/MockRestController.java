package be.odisee.brainstorm.controller.api;

import be.odisee.brainstorm.dao.RequestRepository;
import be.odisee.brainstorm.domain.LegalFly.Request;
import be.odisee.brainstorm.dto.*;
import be.odisee.brainstorm.service.LegalFly.DocumentAnonymizeService;
import be.odisee.brainstorm.service.LegalFly.MockDataService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/mock")
@CrossOrigin
public class MockRestController {

    private final RequestRepository requestRepo;
    private final MockDataService mockDataService;
    private final DocumentAnonymizeService documentAnonymizeService;

    public MockRestController(RequestRepository requestRepo,
                              MockDataService mockDataService,
                              DocumentAnonymizeService documentAnonymizeService) {
        this.requestRepo = requestRepo;
        this.mockDataService = mockDataService;
        this.documentAnonymizeService = documentAnonymizeService;
    }

    @PostMapping("/{requestId}/generate")
    public MockResponse generate(@PathVariable Integer requestId,
                                 @RequestBody MockFieldSelection selection) {
        Request r = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request niet gevonden: " + requestId));

        Map<String,String> original = new LinkedHashMap<>();
        original.put("Naam",   (r.getJurist()!=null) ? (r.getJurist().getFirstName()+" "+r.getJurist().getLastName()) : "");
        original.put("E-mail", (r.getJurist()!=null) ? r.getJurist().getEmail() : "");

        List<MockRow> rows = new ArrayList<>();
        for (String field : selection.fields()) {
            String key     = normalize(field);
            String origVal = original.getOrDefault(key, "");
            String mockVal = mockDataService.mockForField(field);
            rows.add(new MockRow(key, origVal, mockVal));
        }
        return new MockResponse(requestId, rows);
    }

    @PostMapping("/{requestId}/replace/{documentId}")
    public Map<String,Object> replace(@PathVariable Integer requestId,
                                      @PathVariable Integer documentId,
                                      @RequestBody ReplaceRequest body) {
        var doc = documentAnonymizeService.replaceInDocument(documentId, body.replacements());
        return Map.of("requestId", requestId, "documentId", documentId, "status", doc.getStatus().name());
    }

    private String normalize(String f){
        String s = f == null ? "" : f.trim().toLowerCase();
        return switch (s) {
            case "naam","name","clientname" -> "Naam";
            case "email","e-mail","clientemail" -> "E-mail";
            case "telefoon","phone","gsm" -> "Telefoon";
            case "adres","address" -> "Adres";
            default -> f;
        };
    }
}
