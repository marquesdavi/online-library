package br.com.online.library.service;

import br.com.online.library.model.Book.Book;
import br.com.online.library.model.Borrow.Borrow;
import br.com.online.library.model.User.UserEntity;
import br.com.online.library.repository.IBookRepository;
import br.com.online.library.repository.IBorrowRepository;
import br.com.online.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import br.com.online.library.util.GetCurrentUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {
    private IUserRepository userRepository;
    private IBorrowRepository borrowRepository;
    private IBookRepository bookRepository;

    @Autowired
    public BorrowService(IUserRepository userRepository, IBorrowRepository loanRepository, IBookRepository bookRepository){
        this.borrowRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void newBorrow(Integer bookId, LocalDate expectedReturnDate){
        Optional<Book> book = bookRepository.findById(bookId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername());

        if (user != null && book.isPresent()) {
            Borrow borrow = new Borrow(
                    book.get().getId(),
                    user.getId(),
                    LocalDateTime.now(),
                    expectedReturnDate
            );
            borrowRepository.save(borrow);
        }
    }

    public List<Borrow> userBorrows(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername());

        return borrowRepository.findBorrowByUserId(user.getId());
    }

}
