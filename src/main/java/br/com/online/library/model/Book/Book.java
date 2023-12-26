package br.com.online.library.model.Book;

import br.com.online.library.model.Image.Image;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "synopsis", length = 800)
    private String synopsis;
    @Column(name = "author")
    private String author;
    @Column(name = "category")
    private BookCategory category;
    @Column(name = "code")
    private String code;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Book(
            String title,
            String description,
            String synopsis,
            String author,
            BookCategory category,
            String code
    ) {
        this.title = title;
        this.description = description;
        this.synopsis = synopsis;
        this.author = author;
        this.category = category;
        this.code = code;
    }
}
