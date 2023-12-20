package br.com.online.library.service;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.model.User.UserEntity;
import br.com.online.library.repository.IBookRepository;
import br.com.online.library.repository.IUserRepository;
import br.com.online.library.security.SecurityUtil;
import br.com.online.library.util.CodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {
    private IBookRepository repository;
    private IUserRepository userRepository;

    public BookService(IBookRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public static Map<Integer, Object> sizeFilter(ArrayList<Book> items) {
        int rowsPerPage = 2;
        int booksPerRow = 3;

        int pagesCount = (items.size() + rowsPerPage * booksPerRow - 1) / (rowsPerPage * booksPerRow);

        ArrayList<ArrayList<ArrayList<Book>>> books = new ArrayList<>();
        int startPos = 0;

        for (int i = 0; i < pagesCount; i++) {
            ArrayList<ArrayList<Book>> page = new ArrayList<>();

            for (int j = 0; j < rowsPerPage; j++) {
                int fromIndex = startPos;
                int toIndex = Math.min(startPos + booksPerRow, items.size());
                ArrayList<Book> row = new ArrayList<>(items.subList(fromIndex, toIndex));
                page.add(row);
                startPos += booksPerRow;
                if (startPos >= items.size()) {
                    break;
                }
            }

            books.add(page);
        }

        ArrayList<Integer> pages = new ArrayList<>();
        for (int i = 0; i < pagesCount; i++) {
            pages.add(i + 1);
        }

        Map<Integer, Object> result = new HashMap<>();
        if (!books.isEmpty() && !pages.isEmpty()){
            result.put(0, books);
            result.put(1, pages);
        }
        return result;
    }

    public void createBook(RegisterBookDTO book) {
        String username = SecurityUtil.getSessionUsername();
        UserEntity user = userRepository.findByUsername(username);

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

    public ArrayList<Book> list() {
        return repository.findAll();
    }

    @Transactional
    public void delete(String code) {
        repository.deleteByCode(code);
    }

    @Transactional
    public void update(String code, String field, String value) {
        if (field.equals("title")) {
            repository.updateTitleByCode(code, value);
        } else if (field.equals("description")) {
            repository.updateDescriptionByCode(code, value);
        } else if (field.equals("author")) {
            repository.updateAuthorByCode(code, value);
        } else if (field.equals("category")) {
            repository.updateCategoryByCode(code, value);
        }
    }

    public BookResponseDTO details(String code) {
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
