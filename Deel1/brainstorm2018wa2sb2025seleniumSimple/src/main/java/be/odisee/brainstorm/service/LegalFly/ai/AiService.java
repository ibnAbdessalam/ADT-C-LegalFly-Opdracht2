package be.odisee.brainstorm.service.LegalFly.ai;

import be.odisee.brainstorm.ai.Answer;
import be.odisee.brainstorm.ai.Question;

public interface AiService {
    Answer askQuestion(Question question);
}
