package br.com.online.library.repository;

import br.com.online.library.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
