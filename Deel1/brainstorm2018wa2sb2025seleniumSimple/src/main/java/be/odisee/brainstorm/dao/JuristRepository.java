package be.odisee.brainstorm.dao;

import be.odisee.brainstorm.domain.LegalFly.Jurist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuristRepository extends JpaRepository<Jurist, Integer> {}