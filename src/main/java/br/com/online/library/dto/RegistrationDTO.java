package br.com.online.library.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
