package com.GestionMalade.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.GestionMalade.entity.Medecin;
import com.GestionMalade.entity.Rendezvous;
import com.GestionMalade.service.MedecinService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MedecinController.class)
public class MedecinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedecinService medecinService;

    private Medecin medecin;
    private List<Medecin> medecinList;
    private Rendezvous rendezvous;

    @BeforeEach
    void setUp() {
        // Initialiser un médecin pour les tests
        medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Dr. Dupont");
        medecin.setSpecialite("Cardiologue");
        medecin.setRendezvous(new ArrayList<>());

        // Initialiser une liste de médecins pour les tests
        medecinList = new ArrayList<>();
        medecinList.add(medecin);

        // Initialiser un rendez-vous pour les tests
        rendezvous = new Rendezvous();
        rendezvous.setId(1L);
        rendezvous.setMedecin(medecin);
    }

    @Test
    void testCreateMedecin() throws Exception {
        when(medecinService.saveMedecin(any(Medecin.class))).thenReturn(medecin);

        mockMvc.perform(post("/medecin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medecin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(medecin.getId()))
                .andExpect(jsonPath("$.nom").value(medecin.getNom()))
                .andExpect(jsonPath("$.specialite").value(medecin.getSpecialite()));
    }

    @Test
    void testGetAllMedecins() throws Exception {
        when(medecinService.getAllMedecins()).thenReturn(medecinList);

        mockMvc.perform(get("/medecin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(medecin.getId()))
                .andExpect(jsonPath("$[0].nom").value(medecin.getNom()))
                .andExpect(jsonPath("$[0].specialite").value(medecin.getSpecialite()));
    }

    @Test
    void testGetMedecinById() throws Exception {
        when(medecinService.getMedecinById(1L)).thenReturn(Optional.of(medecin));

        mockMvc.perform(get("/medecin/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medecin.getId()))
                .andExpect(jsonPath("$.nom").value(medecin.getNom()))
                .andExpect(jsonPath("$.specialite").value(medecin.getSpecialite()));
    }

    @Test
    void testGetMedecinByIdNotFound() throws Exception {
        when(medecinService.getMedecinById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/medecin/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateMedecin() throws Exception {
        when(medecinService.existsById(1L)).thenReturn(true);
        when(medecinService.updateMedecin(any(Medecin.class))).thenReturn(medecin);

        mockMvc.perform(put("/medecin/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medecin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medecin.getId()))
                .andExpect(jsonPath("$.nom").value(medecin.getNom()))
                .andExpect(jsonPath("$.specialite").value(medecin.getSpecialite()));
    }

    @Test
    void testUpdateMedecinNotFound() throws Exception {
        when(medecinService.existsById(1L)).thenReturn(false);

        mockMvc.perform(put("/medecin/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medecin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMedecin() throws Exception {
        when(medecinService.existsById(1L)).thenReturn(true);
        doNothing().when(medecinService).deleteMedecin(1L);

        mockMvc.perform(delete("/medecin/1"))
                .andExpect(status().isNoContent());

        verify(medecinService, times(1)).deleteMedecin(1L);
    }

    @Test
    void testDeleteMedecinNotFound() throws Exception {
        when(medecinService.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/medecin/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAssignerMedecinRendezVous() throws Exception {
        when(medecinService.assignerMedecinRendezVous(1L, 1L)).thenReturn(rendezvous);

        mockMvc.perform(post("/1/rendezvous/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezvous.getId()));
    }

    @Test
    void testAssignerMedecinRendezVousError() throws Exception {
        when(medecinService.assignerMedecinRendezVous(1L, 1L)).thenThrow(new RuntimeException("Erreur"));

        mockMvc.perform(post("/1/rendezvous/1"))
                .andExpect(status().isBadRequest());
    }
} 