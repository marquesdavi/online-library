package br.com.online.library.model.Book;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
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
    @Column(name = "author")
    private String author;
    @Column(name = "category")
    private BookCategory category;
    @Column(name = "code")
    private String code;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Book(
            String title,
            String description,
            String author,
            BookCategory category,
            String code
    ) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.code = code;
    }
}
