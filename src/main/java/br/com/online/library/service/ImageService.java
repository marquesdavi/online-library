package br.com.online.library.service;

import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Image.Image;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ImageService {
    public Image create(Image image);
    public List<Image> viewAll();
    public Image viewById(Integer id);
    public Image viewByBookId(Integer bookId);
    void updateBookImage(Integer id, Book book);
}
