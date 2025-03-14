package com.GestionMalade.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import lombok.Data;
import jakarta.persistence.CascadeType;

@Entity
@Table(name="Patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idPatient;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private String adresse;
    

    
    @OneToMany(mappedBy = "patient")
    private List<Rendezvous> rendezvous = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "suivi_medical",
        joinColumns = @JoinColumn(name = "id_patient"),
        inverseJoinColumns = @JoinColumn(name = "medecin_id")
    )
    private List<Medecin> medecins = new ArrayList<>();
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id_patient")
    private Patient patient;
    
    public List<Rendezvous> getRendezvous() {
        return rendezvous;
    }
    
    public void addRendezvous(Rendezvous rdv) {
        this.rendezvous.add(rdv);
    }

    public List<Medecin> getMedecins() {
        return medecins;
    }

    public String getId() {
        return String.valueOf(idPatient);
    }


}