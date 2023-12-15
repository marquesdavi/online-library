package br.com.online.library.controller;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    enum TableField {
        title,
        description,
        author,
        category
    }

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{page}")
    public String index(Model model, @PathVariable(value = "page") Integer page) {

        Map<Integer, Object> bookMap = BookService.sizeFilter(bookService.list());

        ArrayList<ArrayList<ArrayList<Book>>> books = (ArrayList<ArrayList<ArrayList<Book>>>) bookMap.get(0);
        ArrayList<Integer> pages = (ArrayList<Integer>) bookMap.get(1);

        ArrayList<ArrayList<Book>> filteredBooks = new ArrayList<>(books.get(page - 1));

        model.addAttribute("books", filteredBooks);
        model.addAttribute("pages", pages);

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
