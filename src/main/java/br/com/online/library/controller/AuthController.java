package br.com.online.library.controller;

import br.com.online.library.dto.RegistrationDTO;
import br.com.online.library.model.User.UserEntity;
import br.com.online.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;


@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "user/user-login";
    }

    @GetMapping("/login-processing")
    public String loginProcessing() {
        return "user/login-processing";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDTO user = new RegistrationDTO();
        model.addAttribute("user", user);
        return "user/user-auth";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDTO user,
                           BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null) {
            result.rejectValue("email", null, "Email is already in use");
        }

        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if (existingUserUsername != null) {
            result.rejectValue("username", null, "Username is already in use");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/user-auth";
        }

        userService.saveUser(user);
        return "redirect:/?success";
    }

}
