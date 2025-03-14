package com.GestionMalade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionMalade.Repos.PatientRepos;
import com.GestionMalade.entity.Patient;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PatientService {

    @Autowired
    private PatientRepos patientRepos;
    
    public Patient findById(Long id) {
        return patientRepos.findById(id).orElse(null);
    }
    
    public Patient save(Patient patient) {
        return patientRepos.save(patient);
    }
    
    public void deleteById(Long id) {
        patientRepos.deleteById(id);
    }
    
    public List<Patient> findAll() {
        Iterable<Patient> patientIterable = patientRepos.findAll();
        return StreamSupport.stream(patientIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
} 