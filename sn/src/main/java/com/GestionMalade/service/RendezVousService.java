package com.GestionMalade.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionMalade.Repos.RendezVousRepository;
import com.GestionMalade.entity.Rendezvous;
import com.ValidationRV;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public List<Rendezvous> findAll() {
        return rendezVousRepository.findAll();
    }

    public Rendezvous saveRendezVous(Rendezvous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public Optional<Rendezvous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }

    public Rendezvous updateRendezVous(Rendezvous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public Optional<Rendezvous> findById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public Rendezvous save(Rendezvous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public boolean existsById(Long id) {
        return rendezVousRepository.existsById(id);
    }

    public void deleteById(Long id) {
        rendezVousRepository.deleteById(id);
    }
    
    
    public Rendezvous changerStatutRendezVous(Long id, ValidationRV statut) {
        Optional<Rendezvous> optionalRV = rendezVousRepository.findById(id);
        if (optionalRV.isPresent()) {
            Rendezvous rendezVous = optionalRV.get();
            rendezVous.setStatut(statut);
            return rendezVousRepository.save(rendezVous);
        }
        return null;
    }
    
   
    public Rendezvous validerRendezVous(Long id) {
        return changerStatutRendezVous(id, ValidationRV.SUCCESS);
    }
    
    
    public Rendezvous confirmerRendezVous(Long id) {
        return changerStatutRendezVous(id, ValidationRV.CONFIRMATION);
    }
    
    
    public Rendezvous annulerRendezVous(Long id) {
        return changerStatutRendezVous(id, ValidationRV.CANCEL);
    }
}
