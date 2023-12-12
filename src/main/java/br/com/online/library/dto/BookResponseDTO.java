package br.com.online.library.dto;

public record BookResponseDTO(
        String id,
        String title,
        String description,
        String author,
        String category,
        String code,
        String createdAt
    ) {
}
