package be.odisee.legalfly.Dao;

import be.odisee.legalfly.Domain.Jurist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuristRepository extends JpaRepository<Jurist, Integer> {
}
