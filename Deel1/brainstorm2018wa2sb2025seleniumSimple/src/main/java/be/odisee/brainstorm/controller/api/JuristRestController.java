package be.odisee.brainstorm.controller.api;

import be.odisee.brainstorm.dao.JuristRepository;
import be.odisee.brainstorm.domain.LegalFly.Jurist;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jurists")
@CrossOrigin
public class JuristRestController {
    private final JuristRepository juristRepository;

    public JuristRestController(JuristRepository juristRepository) {
        this.juristRepository = juristRepository;
    }

    @GetMapping
    public List<Jurist> list() {
        return juristRepository.findAll();
    }
}