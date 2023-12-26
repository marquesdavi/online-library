package br.com.online.library.controller;

import br.com.online.library.dto.ImageCreateDTO;
import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Image.Image;
import br.com.online.library.service.BookService;
import br.com.online.library.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/img")
public class ImageController {
    private ImageService imageService;
    private BookService bookService;

    public ImageController(ImageService imageService, BookService bookService){
        this.imageService = imageService;
        this.bookService = bookService;
    }

    // display image
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("bookId") Integer bookId) throws IOException, SQLException
    {
        Image image = imageService.viewByBookId(bookId);
        byte [] imageBytes = null;
        imageBytes = image.getImage().getBytes(1,(int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    // add image - post
    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("bookId") Integer bookId) throws IOException, SerialException, SQLException {

        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        var book = bookService.getById(bookId);

        Image image = new Image();
        image.setImage(blob);
        image.setBook(book);
        var savedImg = imageService.create(image);

        bookService.updateImage(bookId, savedImg);

        imageService.updateBookImage(savedImg.getId(), book);

        return "redirect:/1";
    }

}
