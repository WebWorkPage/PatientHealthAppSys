package com.healthcare.healthapp.model;

import jakarta.persistence.*;

@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User patient;
    
    @ManyToOne
    private User doctor;

    public User getDoctor() {
		return doctor;
	}
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	private String medicationName;
    private String dosage;
    private String instructions;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getPatient() {
		return patient;
	}
	public void setPatient(User patient) {
		this.patient = patient;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

    // Getters and Setters
    
}