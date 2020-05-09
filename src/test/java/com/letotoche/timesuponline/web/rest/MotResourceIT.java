package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.Mot;
import com.letotoche.timesuponline.repository.MotRepository;
import com.letotoche.timesuponline.service.MotService;

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

import com.letotoche.timesuponline.domain.enumeration.EtatMot;
/**
 * Integration tests for the {@link MotResource} REST controller.
 */
@SpringBootTest(classes = TimesuponlineApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MotResourceIT {

    private static final String DEFAULT_MOT = "AAAAAAAAAA";
    private static final String UPDATED_MOT = "BBBBBBBBBB";

    private static final EtatMot DEFAULT_ETAT = EtatMot.A_DEVINER;
    private static final EtatMot UPDATED_ETAT = EtatMot.PASSE;

    @Autowired
    private MotRepository motRepository;

    @Autowired
    private MotService motService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotMockMvc;

    private Mot mot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mot createEntity(EntityManager em) {
        Mot mot = new Mot()
            .mot(DEFAULT_MOT)
            .etat(DEFAULT_ETAT);
        return mot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mot createUpdatedEntity(EntityManager em) {
        Mot mot = new Mot()
            .mot(UPDATED_MOT)
            .etat(UPDATED_ETAT);
        return mot;
    }

    @BeforeEach
    public void initTest() {
        mot = createEntity(em);
    }

    @Test
    @Transactional
    public void createMot() throws Exception {
        int databaseSizeBeforeCreate = motRepository.findAll().size();

        // Create the Mot
        restMotMockMvc.perform(post("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mot)))
            .andExpect(status().isCreated());

        // Validate the Mot in the database
        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeCreate + 1);
        Mot testMot = motList.get(motList.size() - 1);
        assertThat(testMot.getMot()).isEqualTo(DEFAULT_MOT);
        assertThat(testMot.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createMotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motRepository.findAll().size();

        // Create the Mot with an existing ID
        mot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotMockMvc.perform(post("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mot)))
            .andExpect(status().isBadRequest());

        // Validate the Mot in the database
        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMotIsRequired() throws Exception {
        int databaseSizeBeforeTest = motRepository.findAll().size();
        // set the field null
        mot.setMot(null);

        // Create the Mot, which fails.

        restMotMockMvc.perform(post("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mot)))
            .andExpect(status().isBadRequest());

        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = motRepository.findAll().size();
        // set the field null
        mot.setEtat(null);

        // Create the Mot, which fails.

        restMotMockMvc.perform(post("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mot)))
            .andExpect(status().isBadRequest());

        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMots() throws Exception {
        // Initialize the database
        motRepository.saveAndFlush(mot);

        // Get all the motList
        restMotMockMvc.perform(get("/api/mots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mot.getId().intValue())))
            .andExpect(jsonPath("$.[*].mot").value(hasItem(DEFAULT_MOT)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getMot() throws Exception {
        // Initialize the database
        motRepository.saveAndFlush(mot);

        // Get the mot
        restMotMockMvc.perform(get("/api/mots/{id}", mot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mot.getId().intValue()))
            .andExpect(jsonPath("$.mot").value(DEFAULT_MOT))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMot() throws Exception {
        // Get the mot
        restMotMockMvc.perform(get("/api/mots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMot() throws Exception {
        // Initialize the database
        motService.save(mot);

        int databaseSizeBeforeUpdate = motRepository.findAll().size();

        // Update the mot
        Mot updatedMot = motRepository.findById(mot.getId()).get();
        // Disconnect from session so that the updates on updatedMot are not directly saved in db
        em.detach(updatedMot);
        updatedMot
            .mot(UPDATED_MOT)
            .etat(UPDATED_ETAT);

        restMotMockMvc.perform(put("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMot)))
            .andExpect(status().isOk());

        // Validate the Mot in the database
        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeUpdate);
        Mot testMot = motList.get(motList.size() - 1);
        assertThat(testMot.getMot()).isEqualTo(UPDATED_MOT);
        assertThat(testMot.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingMot() throws Exception {
        int databaseSizeBeforeUpdate = motRepository.findAll().size();

        // Create the Mot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotMockMvc.perform(put("/api/mots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mot)))
            .andExpect(status().isBadRequest());

        // Validate the Mot in the database
        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMot() throws Exception {
        // Initialize the database
        motService.save(mot);

        int databaseSizeBeforeDelete = motRepository.findAll().size();

        // Delete the mot
        restMotMockMvc.perform(delete("/api/mots/{id}", mot.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mot> motList = motRepository.findAll();
        assertThat(motList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
