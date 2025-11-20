package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.dao.DocumentRepository;
import be.odisee.brainstorm.domain.LegalFly.Document;
import be.odisee.brainstorm.domain.LegalFly.documentState;
import be.odisee.brainstorm.service.LegalFly.ai.AiService; // Import your AI Service
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class DocumentAnonymizeService {

    private final DocumentRepository documentRepository;
    private final AiService aiService; // Add this field

    // Update constructor to include AiService
    public DocumentAnonymizeService(DocumentRepository documentRepository, AiService aiService) {
        this.documentRepository = documentRepository;
        this.aiService = aiService;
    }

    /** * NEW: Smart Anonymization using AI
     * 1. Fetches the document
     * 2. Asks AI to find sensitive names
     * 3. Replaces them using the existing logic
     */
    @Transactional
    public Document smartAnonymize(int documentId) {
        // 1. Get the document
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document niet gevonden: " + documentId));

        String textToAnalyze = doc.getText() == null ? "" : doc.getText();

        // 2. Ask AI for suggestions
        Map<String, String> aiSuggestions = aiService.suggestAnonymization(textToAnalyze);

        // 3. Apply the replacements
        return replaceInDocument(documentId, aiSuggestions);
    }

    /** Vervangt strings in de documenttekst en markeert als MOCKED (persistente update). */
    @Transactional
    public Document replaceInDocument(int documentId, Map<String, String> replacements) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document niet gevonden: " + documentId));

        // PAS AAN indien jouw veld anders heet dan 'text'
        String text = doc.getText() == null ? "" : doc.getText();

        for (var e : replacements.entrySet()) {
            String from = e.getKey();
            String to   = e.getValue();
            if (from != null && to != null && !from.isEmpty()) {
                text = text.replace(from, to);
            }
        }

        doc.setText(text);
        doc.setStatus(documentState.MOCKED);
        return documentRepository.save(doc);
    }
}