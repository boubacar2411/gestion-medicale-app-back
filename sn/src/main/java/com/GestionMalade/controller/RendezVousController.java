package com.GestionMalade.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GestionMalade.entity.Medecin;
import com.GestionMalade.entity.Patient;
import com.GestionMalade.entity.Rendezvous;
import com.GestionMalade.exception.ResourceNotFoundException;
import com.GestionMalade.service.MedecinService;
import com.GestionMalade.service.PatientService;
import com.GestionMalade.service.RendezVousService;
import com.ValidationRV;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/rendezvous")
public class RendezVousController {
   
    @Autowired
    private RendezVousService rendezVousService;
    
    @Autowired
    private MedecinService medecinService;
    
    @Autowired
    private PatientService patientService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Rendezvous>> getAllRendezVous() {
        List<Rendezvous> rendezVousList = rendezVousService.findAll();
        return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rendezvous> getRendezVous(@PathVariable Long id) {
        Rendezvous rendezVous = rendezVousService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("RendezVous not found"));
        
        // Charger explicitement le médecin
        Medecin medecin = medecinService.getMedecinById(rendezVous.getMedecin().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Medecin not found"));
        rendezVous.setMedecin(medecin);
        
        return ResponseEntity.ok(rendezVous);
    }
    
    @PostMapping
    public ResponseEntity<Rendezvous> createRendezVous(@RequestBody Rendezvous rendezVous) {
        try {
            // Vérifier si le médecin existe
            Medecin medecin = medecinService.getMedecinById(rendezVous.getMedecin().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'ID : " 
                    + rendezVous.getMedecin().getId()));
            
            // Vérifier si le patient existe et le récupérer depuis la base de données
            if (rendezVous.getPatient() != null && rendezVous.getPatient().getId() != null) {
                Patient patient = patientService.findById(Long.parseLong(rendezVous.getPatient().getId()));
                if (patient == null) {
                    throw new ResourceNotFoundException("Patient non trouvé avec l'ID : " + rendezVous.getPatient().getId());
                }
                rendezVous.setPatient(patient);
            } else {
                // Si aucun patient n'est fourni, définir explicitement à null pour éviter les références transientes
                rendezVous.setPatient(null);
            }
            
            // Vérifier si le médecin a déjà un rendez-vous à cette date et heure
            for (Rendezvous rdv : medecin.getRendezvous()) {
                if (rdv.getDateHeure() != null && rendezVous.getDateHeure() != null && 
                    rdv.getDateHeure().equals(rendezVous.getDateHeure())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            
            // Associer le médecin existant au rendez-vous
            rendezVous.setMedecin(medecin);
            
            // Sauvegarder le rendez-vous
            Rendezvous newRendezVous = rendezVousService.save(rendezVous);
            return new ResponseEntity<>(newRendezVous, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log l'exception pour le débogage
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Rendezvous> updateRendezVous(@PathVariable Long id, @RequestBody Rendezvous rendezVous) {
        if (!rendezVousService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Vérifier si le médecin existe
        if (rendezVous.getMedecin() != null && rendezVous.getMedecin().getId() != null) {
            Medecin medecin = medecinService.getMedecinById(rendezVous.getMedecin().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'ID : " 
                    + rendezVous.getMedecin().getId()));
            rendezVous.setMedecin(medecin);
        }
        
        // Vérifier si le patient existe
        if (rendezVous.getPatient() != null && rendezVous.getPatient().getId() != null) {
            Patient patient = patientService.findById(Long.parseLong(rendezVous.getPatient().getId()));
            if (patient == null) {
                throw new ResourceNotFoundException("Patient non trouvé avec l'ID : " + rendezVous.getPatient().getId());
            }
            rendezVous.setPatient(patient);
        }
        rendezVous.setId(id);
        Rendezvous updatedRendezVous = rendezVousService.save(rendezVous);
        return new ResponseEntity<>(updatedRendezVous, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        if (!rendezVousService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rendezVousService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
   
    @PutMapping("/{id}/valider")
    public ResponseEntity<Rendezvous> validerRendezVous(@PathVariable Long id) {
        try {
            // Vérifier d'abord si le rendez-vous existe
            if (!rendezVousService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            Rendezvous rendezVous = rendezVousService.validerRendezVous(id);
            if (rendezVous == null) {
                throw new ResourceNotFoundException("Rendez-vous non trouvé avec l'ID : " + id);
            }
            return ResponseEntity.ok(rendezVous);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
    @PutMapping("/{id}/confirmer")
    public ResponseEntity<Rendezvous> confirmerRendezVous(@PathVariable Long id) {
        try {
            // Vérifier d'abord si le rendez-vous existe
            if (!rendezVousService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            Rendezvous rendezVous = rendezVousService.confirmerRendezVous(id);
            if (rendezVous == null) {
                throw new ResourceNotFoundException("Rendez-vous non trouvé avec l'ID : " + id);
            }
            return ResponseEntity.ok(rendezVous);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
    @PutMapping("/{id}/annuler")
    public ResponseEntity<Rendezvous> annulerRendezVous(@PathVariable Long id) {
        try {
            // Vérifier d'abord si le rendez-vous existe
            if (!rendezVousService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            Rendezvous rendezVous = rendezVousService.annulerRendezVous(id);
            if (rendezVous == null) {
                throw new ResourceNotFoundException("Rendez-vous non trouvé avec l'ID : " + id);
            }
            return ResponseEntity.ok(rendezVous);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
    @PutMapping("/{id}/statut")
    public ResponseEntity<Rendezvous> changerStatutRendezVous(
            @PathVariable Long id, 
            @RequestParam ValidationRV statut) {
        try {
            // Vérifier d'abord si le rendez-vous existe
            if (!rendezVousService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            Rendezvous rendezVous = rendezVousService.changerStatutRendezVous(id, statut);
            if (rendezVous == null) {
                throw new ResourceNotFoundException("Rendez-vous non trouvé avec l'ID : " + id);
            }
            return ResponseEntity.ok(rendezVous);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
