package com.healthcare.healthapp.controller;

import com.healthcare.healthapp.model.User;
import com.healthcare.healthapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterFormLoads() throws Exception {
        mockMvc.perform(get("/register"))
               .andExpect(status().isOk())
               .andExpect(view().name("register"));
    }

    @Test
    void testLoginFormLoads() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"));
    }

    @Test
    void testRegisterPostRedirectsToLogin() throws Exception {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        mockMvc.perform(post("/register")
                .param("username", "testuser")
                .param("password", "testpass")
                .param("role", "PATIENT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
