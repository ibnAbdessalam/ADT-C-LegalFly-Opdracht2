package be.odisee.legalfly.Dao;

import be.odisee.legalfly.Domain.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction, Integer> {
}
