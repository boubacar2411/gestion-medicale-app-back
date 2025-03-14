package com.GestionMalade.entity;

import java.util.ArrayList;
import java.util.List;

import com.ValidationRV;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "medecin")
@Data
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String specialite;
    private String tel;
    private String email;
    private String adresse;

    
    @OneToMany(mappedBy = "medecin")
    private List<Rendezvous> rendezvous = new ArrayList<>();
    
    @ManyToMany(mappedBy = "medecins")
    private List<Patient> patients = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private ValidationRV statut;
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getSpecialite() {
        return specialite;
    }
    
    public String getTel() {
        return tel;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    
    
    public List<Patient> getPatients() {
        return patients;
    }
    
    public List<Rendezvous> getRendezvous() {
        return rendezvous;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    
    
    
}
