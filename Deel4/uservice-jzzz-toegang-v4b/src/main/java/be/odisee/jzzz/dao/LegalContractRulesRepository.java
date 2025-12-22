package be.odisee.jzzz.dao;

import be.odisee.jzzz.domain.LegalContractRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LegalContractRulesRepository  extends JpaRepository<LegalContractRules, Long> {
}
