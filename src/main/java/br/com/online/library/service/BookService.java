package br.com.online.library.service;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.repository.IBookRepository;
import br.com.online.library.util.CodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    private IBookRepository repository;
    public BookService(IBookRepository repository){
        this.repository = repository;
    }

    public void createBook(RegisterBookDTO book){
        boolean categoryMatch = Arrays
                .stream(BookCategory.values())
                .anyMatch(n -> n.toString().equals(book.category()));

        var newBook = new Book(
                book.title(),
                book.description(),
                book.author(),
                (categoryMatch ? BookCategory.valueOf(book.category()) : BookCategory.Other),
                CodeGenerator.newCode()
        );

        repository.save(newBook);
    }

    public List<Book> list(){
        return repository.findAll();
    }

    @Transactional
    public void delete(String code){
        repository.deleteByCode(code);
    }

    @Transactional
    public void update(String code, String field, String value){
        if (field.equals("title")){
            repository.updateTitleByCode(code, value);
        } else if (field.equals("description")) {
            repository.updateDescriptionByCode(code, value);
        } else if (field.equals("author")) {
            repository.updateAuthorByCode(code, value);
        } else if (field.equals("category")) {
            repository.updateCategoryByCode(code, value);
        }
    }

    public BookResponseDTO details(String code){
        var book = repository.findBookByCode(code);

        return new BookResponseDTO(
                book.getId().toString(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthor(),
                book.getCategory().toString(),
                book.getCode(),
                book.getCreatedAt().toString()
        );
    }
}
