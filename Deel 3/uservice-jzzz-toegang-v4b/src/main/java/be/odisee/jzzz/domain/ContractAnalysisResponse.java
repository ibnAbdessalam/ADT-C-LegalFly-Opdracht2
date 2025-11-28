package be.odisee.jzzz.domain;

import java.util.List;

public record ContractAnalysisResponse(
        String summary,
        List<String> risks,
        String recommendation
) {}