package com.GestionMalade.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.GestionMalade.entity.Patient;

@Repository
public interface PatientRepos extends JpaRepository<Patient, Long> {
}
