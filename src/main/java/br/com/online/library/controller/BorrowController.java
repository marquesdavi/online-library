package br.com.online.library.controller;

import br.com.online.library.dto.BookBorrowDTO;
import br.com.online.library.service.BookService;
import br.com.online.library.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableMethodSecurity
@Controller
public class BorrowController {
    private BorrowService borrowService;
    private BookService bookService;

    public BorrowController(BorrowService borrowService, BookService bookService){
        this.borrowService = borrowService;
        this.bookService = bookService;
    }

    @GetMapping("/borrow")
    public String showBorrowPage(Model model, @Valid @RequestParam Integer bookId){
        model.addAttribute("bookId", bookId);
        return "borrow/borrow";
    }

    @PostMapping("/borrow")
    public String createBorrow(@Valid BookBorrowDTO bookBorrowDTO){
        borrowService.newBorrow(bookBorrowDTO.bookId(), bookBorrowDTO.expectedReturnDate());
        return "redirect:/1";
    }

    @GetMapping("/me/borrow")
    public String userBorrows(Model model){
        var borrows = borrowService.userBorrows();
        var book = bookService;
        model.addAttribute("borrows", borrows);
        model.addAttribute("book", book);

        return "borrow/user-borrows";
    }
}
