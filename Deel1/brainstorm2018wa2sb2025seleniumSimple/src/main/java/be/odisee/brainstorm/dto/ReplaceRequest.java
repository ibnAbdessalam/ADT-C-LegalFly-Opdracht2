package be.odisee.brainstorm.dto;
import java.util.Map;

/** Mapping van origineel -> mock die effectief in het document moet worden vervangen. */
public record ReplaceRequest(Map<String,String> replacements) { }
