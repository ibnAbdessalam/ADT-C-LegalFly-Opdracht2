package be.odisee.jzzz.domain;

public record ContractAnalysisRequest(
        String contractText,
        String question
) {}
