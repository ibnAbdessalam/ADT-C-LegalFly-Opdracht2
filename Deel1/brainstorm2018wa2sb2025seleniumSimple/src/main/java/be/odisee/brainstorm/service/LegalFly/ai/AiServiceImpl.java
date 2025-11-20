package be.odisee.brainstorm.service.LegalFly.ai;

import be.odisee.brainstorm.ai.Answer;
import be.odisee.brainstorm.ai.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;

    public AiServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer askQuestion(Question question) {
        var answerText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();
        return new Answer(answerText);
    }

    @Override
    public Map<String, String> suggestAnonymization(String text) {
        // We ask the AI to extract names and return them as a JSON Map
        return chatClient.prompt()
                .user(u -> u.text("""
                        Analyze the following legal text. Identify all names of natural persons (people).
                        Return a JSON map where:
                        - The Key is the full name found in the text.
                        - The Value is the string "ANONYMIZED_NAME".
                        
                        If no names are found, return an empty map.
                        
                        Text to analyze:
                        {input_text}
                        """)
                        .param("input_text", text))
                .call()
                .entity(new ParameterizedTypeReference<Map<String, String>>() {});
    }
}