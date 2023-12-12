package br.com.online.library.model.Borrow;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_borrow")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer bookId;
    private Integer userId;
    private LocalDateTime borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDateTime returnDate;
    private boolean isLate;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Borrow(Integer bookId, Integer userId, LocalDateTime borrowDate, LocalDate expectedReturnDate){
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.isLate = false;
    }
}
