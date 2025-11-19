package be.odisee.brainstorm.controller.ai;

import be.odisee.brainstorm.ai.Answer;
import be.odisee.brainstorm.ai.Question;
import be.odisee.brainstorm.service.LegalFly.ai.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskController {

    private final AiService aiService;

    public AskController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(path="/ask", produces="application/json")
    public Answer ask(@RequestBody Question question) {
        return aiService.askQuestion(question);
    }
}
