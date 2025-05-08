package com.healthcare.healthapp.controller;

import org.junit.jupiter.api.BeforeEach;
import com.healthcare.healthapp.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.healthcare.healthapp.repository.UserRepository;
import com.healthcare.healthapp.service.CustomUserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository; // Mocking the repository

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService; // Injecting the mock into the service

    private User mockUser;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Setting up a mock user object to test
        mockUser = new User();
        mockUser.setUsername("johndoe");
        mockUser.setPassword("password123"); // In a real case, this should be a hashed password
        mockUser.setRole("ROLE_USER"); // ✅ pass a string, not a set

    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        // Arrange: Mock the behavior of userRepository
        when(userRepository.findByUsername("johndoe")).thenReturn(mockUser);

        // Act: Call the service method
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("johndoe");

        // Assert: Verify the result
        assertNotNull(userDetails);
        assertEquals("johndoe", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange: Mock the repository to return null when the user doesn't exist
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        // Act & Assert: Verify that the service throws a UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonexistentuser");
        });
    }

    @Test
    public void testLoadUserByUsername_EmptyUsername() {
        // Act & Assert: Check if empty username throws exception
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("");
        });
    }
}

