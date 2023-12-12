package br.com.online.library.repository;

import br.com.online.library.model.Borrow.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findBorrowByUserId(Integer userId);
}
