package br.com.online.library.service.implementation;

import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Image.Image;
import br.com.online.library.repository.IImageRepository;
import br.com.online.library.service.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private IImageRepository imageRepository;

    public ImageServiceImpl(IImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }
    @Override
    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }
    @Override
    public Image viewById(Integer id) {
        return imageRepository.findById(id).get();
    }

    @Override
    public Image viewByBookId(Integer bookId) {
        return imageRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public void updateBookImage(Integer id, Book book) {
        imageRepository.updateImageBookById(id, book);
    }
}