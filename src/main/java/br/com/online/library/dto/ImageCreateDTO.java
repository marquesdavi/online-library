package br.com.online.library.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImageCreateDTO(
        MultipartFile file,
        Integer bookId
) {
}
