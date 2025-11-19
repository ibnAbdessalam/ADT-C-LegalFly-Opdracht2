package be.odisee.brainstorm.dao;

import be.odisee.brainstorm.domain.LegalFly.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockPersoonRepository extends JpaRepository<Persoon, Integer> {
    Persoon findByEmailadres(String email);
    Persoon findByVoornaam(String voornaam);
}