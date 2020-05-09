package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.Reclamation;
import com.letotoche.timesuponline.repository.ReclamationRepository;
import com.letotoche.timesuponline.service.ReclamationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.letotoche.timesuponline.domain.enumeration.EtatReclamation;
/**
 * Integration tests for the {@link ReclamationResource} REST controller.
 */
@SpringBootTest(classes = TimesuponlineApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReclamationResourceIT {

    private static final EtatReclamation DEFAULT_ETAT = EtatReclamation.EMISE;
    private static final EtatReclamation UPDATED_ETAT = EtatReclamation.ACCEPTEE;

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReclamationMockMvc;

    private Reclamation reclamation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reclamation createEntity(EntityManager em) {
        Reclamation reclamation = new Reclamation()
            .etat(DEFAULT_ETAT);
        return reclamation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reclamation createUpdatedEntity(EntityManager em) {
        Reclamation reclamation = new Reclamation()
            .etat(UPDATED_ETAT);
        return reclamation;
    }

    @BeforeEach
    public void initTest() {
        reclamation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReclamation() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation
        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isCreated());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate + 1);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createReclamationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation with an existing ID
        reclamation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = reclamationRepository.findAll().size();
        // set the field null
        reclamation.setEtat(null);

        // Create the Reclamation, which fails.

        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isBadRequest());

        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReclamations() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        // Get all the reclamationList
        restReclamationMockMvc.perform(get("/api/reclamations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reclamation.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", reclamation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reclamation.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReclamation() throws Exception {
        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReclamation() throws Exception {
        // Initialize the database
        reclamationService.save(reclamation);

        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Update the reclamation
        Reclamation updatedReclamation = reclamationRepository.findById(reclamation.getId()).get();
        // Disconnect from session so that the updates on updatedReclamation are not directly saved in db
        em.detach(updatedReclamation);
        updatedReclamation
            .etat(UPDATED_ETAT);

        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReclamation)))
            .andExpect(status().isOk());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingReclamation() throws Exception {
        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Create the Reclamation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReclamation() throws Exception {
        // Initialize the database
        reclamationService.save(reclamation);

        int databaseSizeBeforeDelete = reclamationRepository.findAll().size();

        // Delete the reclamation
        restReclamationMockMvc.perform(delete("/api/reclamations/{id}", reclamation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
