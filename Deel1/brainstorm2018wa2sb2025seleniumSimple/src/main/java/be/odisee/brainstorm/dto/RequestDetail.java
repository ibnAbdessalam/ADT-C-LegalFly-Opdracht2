package be.odisee.brainstorm.dto;

public record RequestDetail(
        Integer id,
        String title,
        String status,
        String clientName,
        String clientEmail,
        String previewText,
        String lawName,
        String lawDescription
) { }