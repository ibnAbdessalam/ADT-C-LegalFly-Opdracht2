package be.odisee.brainstorm.dao;

import be.odisee.brainstorm.domain.LegalFly.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Document findByTitle(String title);
}
