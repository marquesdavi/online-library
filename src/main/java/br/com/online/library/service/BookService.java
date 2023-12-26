package br.com.online.library.service;

import br.com.online.library.dto.BookResponseDTO;
import br.com.online.library.dto.RegisterBookDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Book.BookCategory;
import br.com.online.library.model.Image.Image;
import br.com.online.library.repository.IBookRepository;
import br.com.online.library.repository.IUserRepository;
import br.com.online.library.util.CodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    private IBookRepository repository;
    private IUserRepository userRepository;
    private ImageService imageService;

    public BookService(IBookRepository repository, IUserRepository userRepository, ImageService imageService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.imageService = imageService;
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

    public Book createBook(RegisterBookDTO book) {
        boolean categoryMatch = Arrays
                .stream(BookCategory.values())
                .anyMatch(n -> n.toString().equals(book.category()));

        var newBook = new Book();
        newBook.setTitle(book.title());
        newBook.setDescription(book.description());
        newBook.setSynopsis(book.synopsis());
        newBook.setAuthor(book.author());
        var bookCategory = (categoryMatch ? BookCategory.valueOf(book.category()) : BookCategory.OTHER);
        newBook.setCategory(bookCategory);
        newBook.setCode(CodeGenerator.newCode());

        return repository.save(newBook);
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
            boolean categoryMatch = Arrays
                    .stream(BookCategory.values())
                    .anyMatch(n -> n.toString().equals(value));

            var bookCategory = (categoryMatch ? BookCategory.valueOf(value) : BookCategory.OTHER);

            repository.updateCategoryByCode(code, bookCategory);
        }
    }

    public BookResponseDTO details(String code) {
        var book = repository.findBookByCode(code);

        return new BookResponseDTO(
                book.getId().toString(),
                book.getTitle(),
                book.getDescription(),
                book.getSynopsis(),
                book.getAuthor(),
                book.getCategory().getName(),
                book.getCode(),
                book.getCreatedAt().toString()
        );
    }

    public Book getById(Integer id){
        return repository.findBookById(id);
    }

    @Transactional
    public void updateImage(Integer id, Image image_id){
        repository.updateImageById(id, image_id);
    }
}
