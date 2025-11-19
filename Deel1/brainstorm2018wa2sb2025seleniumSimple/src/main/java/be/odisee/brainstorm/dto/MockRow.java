package be.odisee.brainstorm.dto;

/** Eén rij in de tabel: veld + originele waarde + mockwaarde. */
public record MockRow(String field, String originalValue, String mockValue) { }
