package com.GestionMalade.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionMalade.entity.Medecin;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByEmail(String email);
}
