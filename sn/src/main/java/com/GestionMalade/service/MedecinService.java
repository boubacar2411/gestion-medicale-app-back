package com.GestionMalade.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionMalade.Repos.MedecinRepository;
import com.GestionMalade.Repos.RendezVousRepository;
import com.GestionMalade.entity.Medecin;
import com.GestionMalade.entity.Rendezvous;

@Service
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;
    
    @Autowired
    private RendezVousRepository rendezVousRepository;
    
    
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }
    
    
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }
    
   
    public Optional<Medecin> getMedecinById(Long id) {
        return medecinRepository.findById(id);
    }
    
    public Medecin updateMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }
   
    public void deleteMedecin(Long id) {
        medecinRepository.deleteById(id);
    }
    
   
    public boolean existsById(Long id) {
        return medecinRepository.existsById(id);
    }
     
    public Rendezvous assignerMedecinRendezVous(Long medecinId, Long rendezVousId) throws Exception {
        
        Medecin medecin = medecinRepository.findById(medecinId)
            .orElseThrow(() -> new Exception("Médecin non trouvé avec l'ID: " + medecinId));
            
        
        Rendezvous rendezVous = rendezVousRepository.findById(rendezVousId)
            .orElseThrow(() -> new Exception("Rendez-vous non trouvé avec l'ID: " + rendezVousId));
            
        
        if (!isMedecinDisponible(medecinId, rendezVous.getDateHeure())) {
            throw new Exception("Le médecin n'est pas disponible à cette date et heure");
        }
        
        rendezVous.setMedecin(medecin);
        
        return rendezVousRepository.save(rendezVous);
    }

    public boolean isMedecinDisponible(Long medecinId, LocalDateTime dateHeure) {
        
        return true; 
    }
}