package com.healthcare.healthapp.repository;

import com.healthcare.healthapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long id);
    List<Appointment> findByDoctorId(Long id);
}