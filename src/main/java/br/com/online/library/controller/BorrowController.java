package br.com.online.library.controller;

import br.com.online.library.dto.BookBorrowDTO;
import br.com.online.library.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BorrowController {
    private BorrowService borrowService;

    public BorrowController(BorrowService borrowService){
        this.borrowService = borrowService;
    }

    @GetMapping("/borrow")
    public String showBorrowPage(Model model, @Valid @RequestParam Integer bookId){
        model.addAttribute("bookId", bookId);
        return "borrow/borrow";
    }

    @PostMapping("/borrow")
    public String createBorrow(@Valid BookBorrowDTO bookBorrowDTO){
        borrowService.newBorrow(bookBorrowDTO.bookId(), bookBorrowDTO.expectedReturnDate());
        return "redirect:/";
    }

    @GetMapping("/me/borrow")
    public String userBorrows(Model model){
        var borrows = borrowService.userBorrows();
        model.addAttribute("borrows", borrows);
        return "borrow/user-borrows";
    }
}
