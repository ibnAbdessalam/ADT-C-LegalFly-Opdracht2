package be.odisee.brainstorm.dto;
import java.util.List;

public record MockResponse(Integer requestId, List<MockRow> rows) { }
