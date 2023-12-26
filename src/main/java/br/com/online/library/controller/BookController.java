package br.com.online.library.controller;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.model.Image.Image;
import br.com.online.library.model.User.Role;
import br.com.online.library.model.User.UserEntity;
import br.com.online.library.security.SecurityUtil;
import br.com.online.library.service.BookService;
import br.com.online.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Blob;
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
    private UserService userService;

    public BookController(BookService bookService, UserService userService){
        this.bookService = bookService;
        this.userService = userService;
    }

    public void validUser(Model model){
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUsername();
        Boolean userRole = false;

        if (username != null){
            user = userService.findByUsername(username);
            userRole = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
            model.addAttribute("roleAdmin", userRole);
        }

        model.addAttribute("roleAdmin", userRole);
    }

    @GetMapping("/{page}")
    public String index(Model model, @PathVariable(value = "page") Integer page) {

        Map<Integer, Object> bookMap = BookService.sizeFilter(bookService.list());

        if (!bookMap.isEmpty()){
            ArrayList<ArrayList<ArrayList<Book>>> books = (ArrayList<ArrayList<ArrayList<Book>>>) bookMap.get(0);
            ArrayList<Integer> pages = (ArrayList<Integer>) bookMap.get(1);

            ArrayList<ArrayList<Book>> filteredBooks = new ArrayList<>(books.get(page - 1));

            validUser(model);

            model.addAttribute("books", filteredBooks);
            model.addAttribute("pages", pages);
            model.addAttribute("currentPage", page);
        }
        else {
            return "redirect:/create/form";
        }

        return "book/index";
    }

    @GetMapping("/create/form")
    public String registerScreen(Model model) {
        model.addAttribute("categorySelect", BookCategory.values());
        return "book/create";
    }

    @PostMapping("/create")
    public String register(RegisterBookDTO book) {
        var newBook = bookService.createBook(book);

        return String.format("redirect:/image?bookId=%d", newBook.getId());
    }

    @GetMapping("/image")
    public String setImageForm(Model model, @RequestParam("bookId") Integer bookId){
        model.addAttribute("bookId", bookId);
        return "book/set-image";
    }
    @PostMapping("/set/image")
    public String setImage(){

        return "book/set-image";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "code") String code) {
        bookService.delete(code);

        return "redirect:/1";
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

        System.out.println(fieldValue);
        bookService.update(code, field, fieldValue);

        return "redirect:/1";
    }

    @GetMapping("/details")
    public String details(Model model, @RequestParam(name = "code") String code, @RequestParam(name = "rootPage") Integer rootPage) {
        BookResponseDTO responseDTO = bookService.details(code);

        validUser(model);

        model.addAttribute("book", responseDTO);
        model.addAttribute("rootPage", rootPage);

        return "book/details";
    }
}
