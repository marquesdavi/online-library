package br.com.online.library.dto;

public record RegisterBookDTO(
        String title,
        String description,
        String author,
        String category
    ) {
}
