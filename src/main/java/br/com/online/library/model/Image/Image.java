package br.com.online.library.model.Image;


import br.com.online.library.model.Book.Book;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name = "image_table")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private Blob image;
    @OneToOne(mappedBy = "image")
    private Book book;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
