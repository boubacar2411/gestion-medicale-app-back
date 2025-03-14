package com.GestionMalade.entity;

import java.time.LocalDateTime;

import com.ValidationRV;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rendezvous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dateHeure;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    
    @ManyToOne
    @JoinColumn(name = "patient_id_patient")
    private Patient patient;
    
    @Enumerated(EnumType.STRING)
    private ValidationRV statut;
    
    // Getters et Setters
    public LocalDateTime getDateHeure() {
        return dateHeure;
    }
    
    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }
    
    public Medecin getMedecin() {
        return medecin;
    }
    
    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public ValidationRV getStatut() {
        return statut;
    }
    
    public void setStatut(ValidationRV statut) {
        this.statut = statut;
    }
}

