package be.odisee.jzzz.service;

import be.odisee.jzzz.dao.LegalContractRulesRepository;
import be.odisee.jzzz.domain.LegalContractRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class legalContractRulesServiceImpl implements legalContractRulesService {

    private final LegalContractRulesRepository legalContractRulesRepository;

    public legalContractRulesServiceImpl(LegalContractRulesRepository legalContractRulesRepository) {
        this.legalContractRulesRepository = legalContractRulesRepository;
    }


    // Query methods
    public List<LegalContractRules> getAllLegalContractRule() {
        return legalContractRulesRepository.findAll();
    }

    public Optional<LegalContractRules> getLegalContractRuleById(Long id) {
        return legalContractRulesRepository.findById(id);
    }

    public LegalContractRules createLegalContractRule(LegalContractRules legalContractRules) {
       return  legalContractRulesRepository.save(legalContractRules);
    }
    public List<LegalContractRules> selectRelevantRulesByTitle(String contractText, List<LegalContractRules> allRules) {
        return allRules.stream()
                .filter(rule -> contractText.toLowerCase().contains(rule.getTitle().toLowerCase()))
                .limit(5) // keep at most 5 relevant rules
                .toList();
    }
}
