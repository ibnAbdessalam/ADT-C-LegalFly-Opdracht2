package be.odisee.brainstorm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import be.odisee.brainstorm.domain.LegalFly.Persoon;

public interface PersoonRepository extends JpaRepository<Persoon, Integer> {
	Persoon findByEmailadres(String email);
	Persoon findByVoornaam(String voornaam);
}
