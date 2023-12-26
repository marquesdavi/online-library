package br.com.online.library.repository;

import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Image.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends CrudRepository<Image, Integer> {
    Image findByBookId(Integer bookId);

    @Modifying
    @Query("UPDATE Image i SET i.book = ?2 WHERE i.id = ?1")
    void updateImageBookById(@Param("id")Integer id, @Param("value") Book value);
}
