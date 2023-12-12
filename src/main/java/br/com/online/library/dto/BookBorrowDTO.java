package br.com.online.library.dto;

import java.time.LocalDate;

public record BookBorrowDTO(
        Integer bookId,
        LocalDate expectedReturnDate
) {
}
