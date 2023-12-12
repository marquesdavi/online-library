package br.com.online.library.service;

import br.com.online.library.dto.RegistrationDTO;
import br.com.online.library.model.User.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);

    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
