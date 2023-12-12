package br.com.online.library.service.implementation;

import br.com.online.library.dto.RegistrationDTO;
import br.com.online.library.model.User.Role;
import br.com.online.library.model.User.UserEntity;
import br.com.online.library.repository.IRoleRepository;
import br.com.online.library.repository.IUserRepository;
import br.com.online.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImplementation implements UserService {
    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDTO registrationDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(userRole));

        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
