package be.odisee.jzzz.service;


import be.odisee.jzzz.domain.LegalContractRules;

import java.util.List;
import java.util.Optional;

public interface legalContractRulesService {
    // Query methods
    List<LegalContractRules> getAllLegalContractRule();

    Optional<LegalContractRules> getLegalContractRuleById(Long id);

    // Command methods
    LegalContractRules createLegalContractRule(LegalContractRules request);

    List<LegalContractRules> selectRelevantRulesByTitle(String contractText, List<LegalContractRules> allRules);
}