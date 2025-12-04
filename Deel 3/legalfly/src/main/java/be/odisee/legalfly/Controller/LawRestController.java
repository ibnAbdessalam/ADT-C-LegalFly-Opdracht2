package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Law;
import be.odisee.legalfly.Service.LawService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/laws")
public class LawRestController {
    private final LawService lawService;

    public LawRestController(LawService lawService) {
        this.lawService = lawService;
    }

    @GetMapping
    public List<Law> getAll() {
        return lawService.getAllLaws();
    }
}
