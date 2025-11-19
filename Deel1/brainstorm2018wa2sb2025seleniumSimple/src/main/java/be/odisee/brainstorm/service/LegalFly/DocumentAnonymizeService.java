package be.odisee.brainstorm.service.LegalFly;

import be.odisee.brainstorm.dao.DocumentRepository;
import be.odisee.brainstorm.domain.LegalFly.Document;
import be.odisee.brainstorm.domain.LegalFly.documentState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class DocumentAnonymizeService {

    private final DocumentRepository documentRepository;

    public DocumentAnonymizeService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
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
