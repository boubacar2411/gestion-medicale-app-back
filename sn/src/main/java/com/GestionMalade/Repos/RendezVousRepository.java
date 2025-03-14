package com.GestionMalade.Repos;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMalade.entity.Rendezvous;

public interface RendezVousRepository extends JpaRepository<Rendezvous, Long> {
   
}
