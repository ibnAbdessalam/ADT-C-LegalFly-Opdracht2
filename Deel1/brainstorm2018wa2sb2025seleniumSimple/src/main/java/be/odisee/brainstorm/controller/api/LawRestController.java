package be.odisee.brainstorm.controller.api;

import be.odisee.brainstorm.dao.LawRepository;
import be.odisee.brainstorm.domain.LegalFly.Law;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/laws")
@CrossOrigin
public class LawRestController {
    private final LawRepository lawRepository;

    public LawRestController(LawRepository lawRepository) {
        this.lawRepository = lawRepository;
    }

    @GetMapping
    public List<Law> list() {
        return lawRepository.findAll();
    }
}