package be.odisee.brainstorm.service.LegalFly.ai;

import be.odisee.brainstorm.ai.Answer;
import be.odisee.brainstorm.ai.Question;
import java.util.Map;

public interface AiService {
    Answer askQuestion(Question question);

    /**
     * Uses AI to analyze text and return a map of sensitive words to anonymize.
     * Key = Original Name, Value = Replacement (e.g., "ANONYMIZED")
     */
    Map<String, String> suggestAnonymization(String text);
}