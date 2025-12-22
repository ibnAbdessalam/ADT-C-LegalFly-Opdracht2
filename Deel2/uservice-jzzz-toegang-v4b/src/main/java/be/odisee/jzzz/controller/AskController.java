package be.odisee.jzzz.controller;

import be.odisee.jzzz.domain.Answer;
import be.odisee.jzzz.domain.ContractAnalysisRequest;
import be.odisee.jzzz.domain.ContractAnalysisResponse;
import be.odisee.jzzz.domain.Question;
import be.odisee.jzzz.service.aiService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AskController {

    private final aiService aiService;

    public AskController(aiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(path="/ask", produces="application/json")
    public Answer ask(@RequestBody Question question) {
        return aiService.askQuestion(question);
    }


    // Nieuwe endpoint voor contract analyse
    @PostMapping(path = "/api/ai/analyze", produces = "application/json")
    public ContractAnalysisResponse analyzeContract(@RequestBody ContractAnalysisRequest request) {
        return aiService.analyzeContract(request);
    }

    // Health check endpoint
    @GetMapping(path = "/api/ai/health")
    public String health() {
        return "AI Service is running";
    }
}






