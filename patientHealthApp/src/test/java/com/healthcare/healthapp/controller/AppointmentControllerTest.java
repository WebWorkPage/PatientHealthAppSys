package com.healthcare.healthapp.controller;

import com.healthcare.healthapp.repository.AppointmentRepository;
import com.healthcare.healthapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentRepository appointmentRepo;

    @MockBean
    private UserRepository userRepo;

    @Test
    void testBookFormLoadsSuccessfully() throws Exception {
        mockMvc.perform(get("/appointments/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_form")) // Update this to match your actual view file
                .andExpect(model().attributeExists("doctors")); // Adjust this attribute name if needed
    }
}
