package br.com.online.library.controller;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    enum TableField {
        title,
        description,
        author,
        category
    }


    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.list());
        return "book/index";
    }

    @GetMapping("/create/form")
    public String registerScreen(Model model) {
        model.addAttribute("categorySelect", BookCategory.values());
        return "book/create";
    }

    @PostMapping("/create")
    public String register(RegisterBookDTO book) {
        bookService.createBook(book);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "code") String code) {
        bookService.delete(code);

        return "redirect:/";
    }

    // Chama a página de inserir dado
    @GetMapping("/update")
    public String update(Model model, @RequestParam(name = "code") String code, @RequestParam(name = "field") String field) {
        model.addAttribute("code", code);
        model.addAttribute("field", field);
        model.addAttribute("categorySelect", BookCategory.values());

        return "book/update";
    }

    @GetMapping("/update/field")
    public String updateField(Model model, @RequestParam(name = "code") String code) {

        model.addAttribute("code", code);
        model.addAttribute("tableField", TableField.values());

        return "book/update-column";
    }

    @GetMapping("/update/save/{code}/{field}/{fieldValue}")
    public String updateSave(@PathVariable(value = "code") String code,
                             @PathVariable(value = "field") String field,
                             @PathVariable(value = "fieldValue") String fieldValue
    ) {
        bookService.update(code, field, fieldValue);

        return "redirect:/";
    }

    @GetMapping("/details")
    public String details(Model model, @RequestParam(name = "code") String code) {
        BookResponseDTO responseDTO = bookService.details(code);

        model.addAttribute("book", responseDTO);

        return "book/details";
    }
}
