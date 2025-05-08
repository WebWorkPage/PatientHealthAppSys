package com.healthcare.healthapp.repository;

import com.healthcare.healthapp.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByPatientId(Long id);
    List<Medication> findByDoctorId(Long id);
}