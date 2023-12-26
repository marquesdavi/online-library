package br.com.online.library.dto;

import org.springframework.web.multipart.MultipartFile;

public record RegisterBookDTO(
        String title,
        String description,
        String synopsis,
        String author,
        String category
    ) {
}
