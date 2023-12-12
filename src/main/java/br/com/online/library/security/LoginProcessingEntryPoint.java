package br.com.online.library.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginProcessingEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (authException == null) {
            // Successful login
            response.sendRedirect("/");
        } else {
            // Authentication failure
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            request.getRequestDispatcher("/login-processing").forward(request, response);
        }
    }
}

