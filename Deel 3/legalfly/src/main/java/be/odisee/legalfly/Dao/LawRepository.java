package be.odisee.legalfly.Dao;

import be.odisee.legalfly.Domain.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawRepository extends JpaRepository<Law, Integer> {
}
