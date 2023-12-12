package br.com.online.library.repository;

import br.com.online.library.model.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findFirstByUsername(String username);
}
