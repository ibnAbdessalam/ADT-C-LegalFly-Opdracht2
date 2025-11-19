package be.odisee.brainstorm.dao;

import be.odisee.brainstorm.domain.LegalFly.Law;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawRepository extends JpaRepository<Law, Integer> {
}