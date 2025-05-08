package com.healthcare.healthapp.config;

import com.healthcare.healthapp.model.User;
import com.healthcare.healthapp.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        System.out.println("Logged in user: " + username);
        System.out.println("Role: " + user.getRole());

        if (user != null && "DOCTOR".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("/dashboard/doctor");
        } else {
            response.sendRedirect("/dashboard/patient");
        }
    }

}
