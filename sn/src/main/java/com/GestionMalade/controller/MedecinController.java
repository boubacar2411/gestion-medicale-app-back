package com.GestionMalade.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.GestionMalade.entity.Medecin;
import com.GestionMalade.entity.Rendezvous;
import com.GestionMalade.service.MedecinService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;
    
    @PostMapping("/medecin")  
    public ResponseEntity<Medecin> createMedecin(@RequestBody Medecin medecin) {
        Medecin savedMedecin = medecinService.saveMedecin(medecin);
        return new ResponseEntity<>(savedMedecin, HttpStatus.CREATED);
    }
    
    @GetMapping("/medecin")
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        List<Medecin> medecins = medecinService.getAllMedecins();
        return new ResponseEntity<>(medecins, HttpStatus.OK);
    }
    
    @GetMapping("/medecin/{id}")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        Optional<Medecin> medecin = medecinService.getMedecinById(id);
        return medecin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/medecin/{id}")
    public ResponseEntity<Medecin> updateMedecin(@PathVariable Long id, @RequestBody Medecin medecin) {
        if (!medecinService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medecin.setId(id);
        Medecin updatedMedecin = medecinService.updateMedecin(medecin);
        return new ResponseEntity<>(updatedMedecin, HttpStatus.OK);
    }
    
    @DeleteMapping("/medecin/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        if (!medecinService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medecinService.deleteMedecin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{medecinId}/rendezvous/{rendezVousId}")
    public ResponseEntity<Rendezvous> assignerMedecinRendezVous(
            @PathVariable Long medecinId, 
            @PathVariable Long rendezVousId) {
        try {
            Rendezvous rendezvous = medecinService.assignerMedecinRendezVous(medecinId, rendezVousId);
            return new ResponseEntity<>(rendezvous, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
