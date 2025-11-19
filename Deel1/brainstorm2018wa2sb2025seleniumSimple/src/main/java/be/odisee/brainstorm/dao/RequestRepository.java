package be.odisee.brainstorm.dao;

import be.odisee.brainstorm.domain.LegalFly.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {}