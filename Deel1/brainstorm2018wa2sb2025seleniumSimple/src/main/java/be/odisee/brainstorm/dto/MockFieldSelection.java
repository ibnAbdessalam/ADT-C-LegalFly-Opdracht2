package be.odisee.brainstorm.dto;
import java.util.List;

/** Welke velden heeft de gebruiker aangevinkt op de dashboard? */
public record MockFieldSelection(List<String> fields) { }
