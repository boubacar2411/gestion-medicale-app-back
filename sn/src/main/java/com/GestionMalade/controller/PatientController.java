package com.GestionMalade.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.GestionMalade.entity.Patient;
import com.GestionMalade.service.PatientService;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> findAll() {
        return patientService.findAll();
    }
    
    @GetMapping("/{id}")
    public Patient findById(@PathVariable Long id) {
        return patientService.findById(id);
    }
    
    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return patientService.save(patient);
    }
}
