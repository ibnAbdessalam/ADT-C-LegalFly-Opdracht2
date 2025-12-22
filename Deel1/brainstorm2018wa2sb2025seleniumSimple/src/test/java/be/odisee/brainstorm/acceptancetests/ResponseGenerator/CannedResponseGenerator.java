package be.odisee.brainstorm.acceptancetests.ResponseGenerator;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.web.reactive.function.BodyInserters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class CannedResponseGenerator {

    private final RestClient restClient;

    public CannedResponseGenerator(
            @Value("${spring.ai.openai.api-key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("API key loaded, length: " + apiKey.length());
            System.out.println("API key prefix: " + apiKey.substring(0, 5) + "*****");
        } else {
            System.out.println("API key not loaded!");
        }

    }



    public void generate(String prompt, String filename) throws Exception {

        Map<String, Object> request = Map.of(
                "model", "gpt-4o-mini",
                "messages", java.util.List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );


        // ⭐ De ruwe JSON ophalen als string
        String json = restClient
                .post()
                .uri("/chat/completions")
                .body(request)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        (req, res) -> {
                            System.out.println("HTTP STATUS: " + res.getStatusCode());
                            System.out.println(new String(res.getBody().readAllBytes()));
                        }
                )
                .body(String.class);

        Path path = Path.of("src/test/resources/canned/" + filename);
        Files.createDirectories(path.getParent());
        Files.writeString(path, json);

        System.out.println("RAW JSON canned response opgeslagen in:");
        System.out.println(path.toAbsolutePath());
    }


    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext ctx =
                SpringApplication.run(CannedResponseGenerator.class, args);

        var generator = ctx.getBean(CannedResponseGenerator.class);

        generator.generate(
                "Hoe zorgt legalfly ervoor dat de informatie die verstuurd wordt niet wordt gebruikt en opgeslagen door AI chatbots?",
                "raw-openai-response.json"
        );


        ctx.close();
    }
}
