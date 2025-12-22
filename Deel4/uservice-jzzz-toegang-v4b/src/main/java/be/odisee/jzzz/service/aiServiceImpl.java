package be.odisee.jzzz.service;

import be.odisee.jzzz.domain.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class aiServiceImpl implements aiService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final legalContractRulesServiceImpl _legalContractRulesService;

    public aiServiceImpl(ChatClient.Builder chatClientBuilder, legalContractRulesServiceImpl _legalContractRulesService) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
        this._legalContractRulesService = _legalContractRulesService;
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
    public ContractAnalysisResponse analyzeContract(ContractAnalysisRequest request) {
        String prompt = buildContractPromptWithRAG(request.contractText(), request.question());

        var response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return parseContractResponse(response);
    }


    public String buildContractPromptWithRAG(String contractText, String question) {
        List<LegalContractRules> legalContractRules = _legalContractRulesService.getAllLegalContractRule();
        List<LegalContractRules> selectedLegalContractRules = _legalContractRulesService.selectRelevantRulesByTitle(contractText, legalContractRules);


        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a legal expert analyzing contracts for LegalFly, a Belgian legal services platform.\n\n");
        prompt.append("CONTRACT TEXT:\n");
        prompt.append(contractText);

        prompt.append("These are some important rules you can use while verifyn the contract.");
        for (LegalContractRules rule : selectedLegalContractRules) {
            prompt.append("- ").append(rule.getTitle()).append(": ").append(rule.getDescription()).append("\n");
        }
        if (question != null && !question.isEmpty()) {
            prompt.append("SPECIFIC QUESTION:\n");
            prompt.append(question);
            prompt.append("\n\n");
        }

        prompt.append("Please provide a detailed analysis including:\n");
        prompt.append("1. A brief summary of the contract (2-3 sentences)\n");
        prompt.append("2. Key risks or concerns (list 3-5 specific items)\n");
        prompt.append("3. Your professional recommendation\n\n");
        prompt.append("IMPORTANT: Format your response as JSON with the following structure:\n");
        prompt.append("{\n");
        prompt.append("  \"summary\": \"...\",\n");
        prompt.append("  \"risks\": [\"risk 1\", \"risk 2\", \"risk 3\"],\n");
        prompt.append("  \"recommendation\": \"...\"\n");
        prompt.append("}");

        return prompt.toString();

    }


    // oude versie, zonder RAG, niet meer gebruikt
    private String buildContractPrompt(String contractText, String question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a legal expert analyzing contracts for LegalFly, a Belgian legal services platform.\n\n");
        prompt.append("CONTRACT TEXT:\n");
        prompt.append(contractText);
        prompt.append("\n\n");

        if (question != null && !question.isEmpty()) {
            prompt.append("SPECIFIC QUESTION:\n");
            prompt.append(question);
            prompt.append("\n\n");
        }

        prompt.append("Please provide a detailed analysis including:\n");
        prompt.append("1. A brief summary of the contract (2-3 sentences)\n");
        prompt.append("2. Key risks or concerns (list 3-5 specific items)\n");
        prompt.append("3. Your professional recommendation\n\n");
        prompt.append("IMPORTANT: Format your response as JSON with the following structure:\n");
        prompt.append("{\n");
        prompt.append("  \"summary\": \"...\",\n");
        prompt.append("  \"risks\": [\"risk 1\", \"risk 2\", \"risk 3\"],\n");
        prompt.append("  \"recommendation\": \"...\"\n");
        prompt.append("}");

        return prompt.toString();
    }

    private ContractAnalysisResponse parseContractResponse(String response) {
        try {
            // Try parsing as JSON first
            String cleanJson = response.trim();
            if (cleanJson.startsWith("```json")) {
                cleanJson = cleanJson.substring(7);
            }
            if (cleanJson.endsWith("```")) {
                cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
            }
            cleanJson = cleanJson.trim();

            if (cleanJson.startsWith("{")) {
                JsonNode jsonNode = objectMapper.readTree(cleanJson);

                String summary = jsonNode.path("summary").asText();
                String recommendation = jsonNode.path("recommendation").asText();

                List<String> risks = new ArrayList<>();
                JsonNode risksNode = jsonNode.path("risks");
                if (risksNode.isArray()) {
                    risksNode.forEach(risk -> risks.add(risk.asText()));
                }

                return new ContractAnalysisResponse(summary, risks, recommendation);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse JSON response: " + e.getMessage());
        }

        // Fallback: parse text response
        return parseTextResponse(response);
    }

    private ContractAnalysisResponse parseTextResponse(String text) {
        String summary = extractSection(text, "summary", "risk");
        String recommendation = extractSection(text, "recommendation", null);
        List<String> risks = extractRisks(text);

        if (summary.isEmpty()) {
            summary = "Contract analysis completed. See details below.";
        }
        if (risks.isEmpty()) {
            risks.add("No specific risks identified in the analysis.");
        }
        if (recommendation.isEmpty()) {
            recommendation = "Please consult with a legal professional for specific advice.";
        }

        return new ContractAnalysisResponse(summary, risks, recommendation);
    }

    private String extractSection(String text, String startMarker, String endMarker) {
        String lowerText = text.toLowerCase();
        int start = lowerText.indexOf(startMarker);

        if (start == -1) return "";

        start = lowerText.indexOf(":", start) + 1;
        int end = endMarker != null ? lowerText.indexOf(endMarker, start) : text.length();

        if (end == -1) end = text.length();
        if (start >= end) return "";

        return text.substring(start, end).trim();
    }

    private List<String> extractRisks(String text) {
        List<String> risks = new ArrayList<>();
        String[] lines = text.split("\n");

        boolean inRisksSection = false;
        for (String line : lines) {
            String lowerLine = line.toLowerCase();

            if (lowerLine.contains("risk") || lowerLine.contains("concern")) {
                inRisksSection = true;
                continue;
            }

            if (inRisksSection && (lowerLine.contains("recommendation") || lowerLine.contains("summary"))) {
                break;
            }

            if (inRisksSection && line.trim().matches("^[\\d\\-\\*•].*")) {
                String risk = line.trim().replaceAll("^[\\d\\-\\*•\\.\\)\\s]+", "");
                if (!risk.isEmpty()) {
                    risks.add(risk);
                }
            }
        }

        return risks;
    }
}