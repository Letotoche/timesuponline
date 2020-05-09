package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.TourDeJeu;
import com.letotoche.timesuponline.repository.TourDeJeuRepository;
import com.letotoche.timesuponline.service.TourDeJeuService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.letotoche.timesuponline.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TourDeJeuResource} REST controller.
 */
@SpringBootTest(classes = TimesuponlineApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TourDeJeuResourceIT {

    private static final Instant DEFAULT_TEMPS_RESTANT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPS_RESTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_DATE_DEBUTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TourDeJeuRepository tourDeJeuRepository;

    @Autowired
    private TourDeJeuService tourDeJeuService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourDeJeuMockMvc;

    private TourDeJeu tourDeJeu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourDeJeu createEntity(EntityManager em) {
        TourDeJeu tourDeJeu = new TourDeJeu()
            .tempsRestant(DEFAULT_TEMPS_RESTANT)
            .dateDebute(DEFAULT_DATE_DEBUTE);
        return tourDeJeu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourDeJeu createUpdatedEntity(EntityManager em) {
        TourDeJeu tourDeJeu = new TourDeJeu()
            .tempsRestant(UPDATED_TEMPS_RESTANT)
            .dateDebute(UPDATED_DATE_DEBUTE);
        return tourDeJeu;
    }

    @BeforeEach
    public void initTest() {
        tourDeJeu = createEntity(em);
    }

    @Test
    @Transactional
    public void createTourDeJeu() throws Exception {
        int databaseSizeBeforeCreate = tourDeJeuRepository.findAll().size();

        // Create the TourDeJeu
        restTourDeJeuMockMvc.perform(post("/api/tour-de-jeus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourDeJeu)))
            .andExpect(status().isCreated());

        // Validate the TourDeJeu in the database
        List<TourDeJeu> tourDeJeuList = tourDeJeuRepository.findAll();
        assertThat(tourDeJeuList).hasSize(databaseSizeBeforeCreate + 1);
        TourDeJeu testTourDeJeu = tourDeJeuList.get(tourDeJeuList.size() - 1);
        assertThat(testTourDeJeu.getTempsRestant()).isEqualTo(DEFAULT_TEMPS_RESTANT);
        assertThat(testTourDeJeu.getDateDebute()).isEqualTo(DEFAULT_DATE_DEBUTE);
    }

    @Test
    @Transactional
    public void createTourDeJeuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tourDeJeuRepository.findAll().size();

        // Create the TourDeJeu with an existing ID
        tourDeJeu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourDeJeuMockMvc.perform(post("/api/tour-de-jeus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourDeJeu)))
            .andExpect(status().isBadRequest());

        // Validate the TourDeJeu in the database
        List<TourDeJeu> tourDeJeuList = tourDeJeuRepository.findAll();
        assertThat(tourDeJeuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTourDeJeus() throws Exception {
        // Initialize the database
        tourDeJeuRepository.saveAndFlush(tourDeJeu);

        // Get all the tourDeJeuList
        restTourDeJeuMockMvc.perform(get("/api/tour-de-jeus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourDeJeu.getId().intValue())))
            .andExpect(jsonPath("$.[*].tempsRestant").value(hasItem(DEFAULT_TEMPS_RESTANT.toString())))
            .andExpect(jsonPath("$.[*].dateDebute").value(hasItem(sameInstant(DEFAULT_DATE_DEBUTE))));
    }
    
    @Test
    @Transactional
    public void getTourDeJeu() throws Exception {
        // Initialize the database
        tourDeJeuRepository.saveAndFlush(tourDeJeu);

        // Get the tourDeJeu
        restTourDeJeuMockMvc.perform(get("/api/tour-de-jeus/{id}", tourDeJeu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourDeJeu.getId().intValue()))
            .andExpect(jsonPath("$.tempsRestant").value(DEFAULT_TEMPS_RESTANT.toString()))
            .andExpect(jsonPath("$.dateDebute").value(sameInstant(DEFAULT_DATE_DEBUTE)));
    }

    @Test
    @Transactional
    public void getNonExistingTourDeJeu() throws Exception {
        // Get the tourDeJeu
        restTourDeJeuMockMvc.perform(get("/api/tour-de-jeus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTourDeJeu() throws Exception {
        // Initialize the database
        tourDeJeuService.save(tourDeJeu);

        int databaseSizeBeforeUpdate = tourDeJeuRepository.findAll().size();

        // Update the tourDeJeu
        TourDeJeu updatedTourDeJeu = tourDeJeuRepository.findById(tourDeJeu.getId()).get();
        // Disconnect from session so that the updates on updatedTourDeJeu are not directly saved in db
        em.detach(updatedTourDeJeu);
        updatedTourDeJeu
            .tempsRestant(UPDATED_TEMPS_RESTANT)
            .dateDebute(UPDATED_DATE_DEBUTE);

        restTourDeJeuMockMvc.perform(put("/api/tour-de-jeus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTourDeJeu)))
            .andExpect(status().isOk());

        // Validate the TourDeJeu in the database
        List<TourDeJeu> tourDeJeuList = tourDeJeuRepository.findAll();
        assertThat(tourDeJeuList).hasSize(databaseSizeBeforeUpdate);
        TourDeJeu testTourDeJeu = tourDeJeuList.get(tourDeJeuList.size() - 1);
        assertThat(testTourDeJeu.getTempsRestant()).isEqualTo(UPDATED_TEMPS_RESTANT);
        assertThat(testTourDeJeu.getDateDebute()).isEqualTo(UPDATED_DATE_DEBUTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTourDeJeu() throws Exception {
        int databaseSizeBeforeUpdate = tourDeJeuRepository.findAll().size();

        // Create the TourDeJeu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourDeJeuMockMvc.perform(put("/api/tour-de-jeus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourDeJeu)))
            .andExpect(status().isBadRequest());

        // Validate the TourDeJeu in the database
        List<TourDeJeu> tourDeJeuList = tourDeJeuRepository.findAll();
        assertThat(tourDeJeuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTourDeJeu() throws Exception {
        // Initialize the database
        tourDeJeuService.save(tourDeJeu);

        int databaseSizeBeforeDelete = tourDeJeuRepository.findAll().size();

        // Delete the tourDeJeu
        restTourDeJeuMockMvc.perform(delete("/api/tour-de-jeus/{id}", tourDeJeu.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TourDeJeu> tourDeJeuList = tourDeJeuRepository.findAll();
        assertThat(tourDeJeuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
