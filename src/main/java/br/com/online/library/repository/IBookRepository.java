package br.com.online.library.repository;

import br.com.online.library.model.Book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface IBookRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query(value = "DELETE FROM book WHERE code = :code ;",
            nativeQuery = true)
    void deleteByCode(@Param("code")String code);

    @Modifying
    @Query("UPDATE Book b SET b.title = ?2 WHERE b.code = ?1")
    void updateTitleByCode(@Param("code")String code, @Param("value")String value);

    @Modifying
    @Query("UPDATE Book b SET b.description = ?2 WHERE b.code = ?1")
    void updateDescriptionByCode(@Param("code")String code, @Param("value")String value);

    @Modifying
    @Query("UPDATE Book b SET b.author = ?2 WHERE b.code = ?1")
    void updateAuthorByCode(@Param("code")String code, @Param("value")String value);

    @Modifying
    @Query("UPDATE Book b SET b.category = ?2 WHERE b.code = ?1")
    void updateCategoryByCode(@Param("code")String code, @Param("value")String value);

    Book findBookByCode(String code);

    ArrayList<Book> findAll();
}
