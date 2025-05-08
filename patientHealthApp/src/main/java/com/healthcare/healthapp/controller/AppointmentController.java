package com.healthcare.healthapp.controller;


import com.healthcare.healthapp.model.Appointment;
import com.healthcare.healthapp.model.User;
import com.healthcare.healthapp.repository.AppointmentRepository;
import com.healthcare.healthapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private UserRepository userRepo;

    // Show form to book appointment
    @GetMapping("/book")
    public String bookForm(Model model) {
        List<User> doctors = userRepo.findAll()
                .stream().filter(user -> user.getRole().equals("DOCTOR")).collect(Collectors.toList());

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        return "appointment_form";
    }

    // Save appointment
    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment,
                                  @RequestParam Long doctorId,
                                  @RequestParam String datetime,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        User patient = userRepo.findByUsername(userDetails.getUsername());
        User doctor = userRepo.findById(doctorId).orElse(null);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(LocalDateTime.parse(datetime));
        appointment.setStatus("Pending");

        appointmentRepo.save(appointment);
        return "redirect:/appointments/my";
    }

    // Show patient’s appointments
    @GetMapping("/my")
    public String viewMyAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User patient = userRepo.findByUsername(userDetails.getUsername());
        List<Appointment> list = appointmentRepo.findByPatientId(patient.getId());
        model.addAttribute("appointments", list);
        return "appointments";
    }

    // Show all appointments (for doctors)
    @GetMapping("/doctor")
    public String doctorAppointments(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User doctor = userRepo.findByUsername(userDetails.getUsername());
        List<Appointment> list = appointmentRepo.findByDoctorId(doctor.getId());
        model.addAttribute("appointments", list);
        return "appointments";
    }
}

