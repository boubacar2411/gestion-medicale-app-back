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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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

import com.ValidationRV;
import com.GestionMalade.entity.Medecin;
import com.GestionMalade.entity.Rendezvous;
import com.GestionMalade.service.MedecinService;
import com.GestionMalade.service.RendezVousService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RendezVousController.class)
public class RendezVousControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RendezVousService rendezVousService;

    @MockBean
    private MedecinService medecinService;

    private Rendezvous rendezVous;
    private Medecin medecin;
    private List<Rendezvous> rendezVousList;

    @BeforeEach
    void setUp() {
        // Initialiser un médecin pour les tests
        medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Dr. Dupont");
        medecin.setSpecialite("Cardiologue");
        medecin.setRendezvous(new ArrayList<>());

        // Initialiser un rendez-vous pour les tests
        rendezVous = new Rendezvous();
        rendezVous.setId(1L);
        rendezVous.setDateHeure(LocalDateTime.now());
        // Nous allons ajouter le motif via réflexion car il n'existe pas dans l'entité
        try {
            java.lang.reflect.Field motifField = Rendezvous.class.getDeclaredField("motif");
            motifField.setAccessible(true);
            motifField.set(rendezVous, "Consultation");
        } catch (Exception e) {
            // Si le champ n'existe pas, nous ne pouvons pas le définir
        }
        rendezVous.setStatut(ValidationRV.SUCCESS); // Utilisation de la valeur correcte de l'énumération
        rendezVous.setMedecin(medecin);

        // Ajouter le rendez-vous à la liste des rendez-vous du médecin
        medecin.getRendezvous().add(rendezVous);

        // Initialiser une liste de rendez-vous pour les tests
        rendezVousList = new ArrayList<>();
        rendezVousList.add(rendezVous);
    }

    @Test
    void testGetAllRendezVous() throws Exception {
        when(rendezVousService.findAll()).thenReturn(rendezVousList);

        mockMvc.perform(get("/api/rendezvous/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(rendezVous.getId()));
    }

    @Test
    void testGetRendezVous() throws Exception {
        when(rendezVousService.findById(1L)).thenReturn(Optional.of(rendezVous));
        when(medecinService.getMedecinById(1L)).thenReturn(Optional.of(medecin));

        mockMvc.perform(get("/api/rendezvous/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testCreateRendezVous() throws Exception {
        when(medecinService.getMedecinById(1L)).thenReturn(Optional.of(medecin));
        when(rendezVousService.save(any(Rendezvous.class))).thenReturn(rendezVous);

        mockMvc.perform(post("/api/rendezvous")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rendezVous)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testUpdateRendezVous() throws Exception {
        when(rendezVousService.existsById(1L)).thenReturn(true);
        when(rendezVousService.save(any(Rendezvous.class))).thenReturn(rendezVous);

        mockMvc.perform(put("/api/rendezvous/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rendezVous)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testDeleteRendezVous() throws Exception {
        when(rendezVousService.existsById(1L)).thenReturn(true);
        doNothing().when(rendezVousService).deleteById(1L);

        mockMvc.perform(delete("/api/rendezvous/1"))
                .andExpect(status().isNoContent());

        verify(rendezVousService, times(1)).deleteById(1L);
    }

    @Test
    void testValiderRendezVous() throws Exception {
        when(rendezVousService.validerRendezVous(1L)).thenReturn(rendezVous);

        mockMvc.perform(put("/api/rendezvous/1/valider"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testConfirmerRendezVous() throws Exception {
        when(rendezVousService.confirmerRendezVous(1L)).thenReturn(rendezVous);

        mockMvc.perform(put("/api/rendezvous/1/confirmer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testAnnulerRendezVous() throws Exception {
        when(rendezVousService.annulerRendezVous(1L)).thenReturn(rendezVous);

        mockMvc.perform(put("/api/rendezvous/1/annuler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }

    @Test
    void testChangerStatutRendezVous() throws Exception {
        when(rendezVousService.changerStatutRendezVous(anyLong(), any(ValidationRV.class))).thenReturn(rendezVous);

        mockMvc.perform(put("/api/rendezvous/1/statut")
                .param("statut", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rendezVous.getId()));
    }
} 