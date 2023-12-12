package br.com.online.library.util;

import br.com.online.library.model.User.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class GetCurrentUser {
    public static UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && principal instanceof UserEntity) {
            return (UserEntity) principal;
        }
        return null;
    }
}
