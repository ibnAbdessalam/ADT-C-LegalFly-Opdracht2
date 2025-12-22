package be.odisee.jzzz.service;

import be.odisee.jzzz.domain.Answer;
import be.odisee.jzzz.domain.ContractAnalysisRequest;
import be.odisee.jzzz.domain.ContractAnalysisResponse;
import be.odisee.jzzz.domain.Question;

public interface aiService {
    Answer askQuestion(Question question);

    ContractAnalysisResponse analyzeContract(ContractAnalysisRequest request);


    String buildContractPromptWithRAG(String contractText, String question);
}