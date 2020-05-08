package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.Partie;
import com.letotoche.timesuponline.domain.User;
import com.letotoche.timesuponline.repository.PartieRepository;
import com.letotoche.timesuponline.service.PartieService;
import com.letotoche.timesuponline.service.dto.PartieCriteria;
import com.letotoche.timesuponline.service.PartieQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartieResource} REST controller.
 */
@SpringBootTest(classes = TimesuponlineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartieResourceIT {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    @Autowired
    private PartieRepository partieRepository;

    @Mock
    private PartieRepository partieRepositoryMock;

    @Mock
    private PartieService partieServiceMock;

    @Autowired
    private PartieService partieService;

    @Autowired
    private PartieQueryService partieQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartieMockMvc;

    private Partie partie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partie createEntity(EntityManager em) {
        Partie partie = new Partie()
            .intitule(DEFAULT_INTITULE);
        return partie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partie createUpdatedEntity(EntityManager em) {
        Partie partie = new Partie()
            .intitule(UPDATED_INTITULE);
        return partie;
    }

    @BeforeEach
    public void initTest() {
        partie = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartie() throws Exception {
        int databaseSizeBeforeCreate = partieRepository.findAll().size();

        // Create the Partie
        restPartieMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partie)))
            .andExpect(status().isCreated());

        // Validate the Partie in the database
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeCreate + 1);
        Partie testPartie = partieList.get(partieList.size() - 1);
        assertThat(testPartie.getIntitule()).isEqualTo(DEFAULT_INTITULE);
    }

    @Test
    @Transactional
    public void createPartieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partieRepository.findAll().size();

        // Create the Partie with an existing ID
        partie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartieMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partie)))
            .andExpect(status().isBadRequest());

        // Validate the Partie in the database
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIntituleIsRequired() throws Exception {
        int databaseSizeBeforeTest = partieRepository.findAll().size();
        // set the field null
        partie.setIntitule(null);

        // Create the Partie, which fails.

        restPartieMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partie)))
            .andExpect(status().isBadRequest());

        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList
        restPartieMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partie.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPartiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(partieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartieMockMvc.perform(get("/api/parties?eagerload=true"))
            .andExpect(status().isOk());

        verify(partieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPartiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(partieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartieMockMvc.perform(get("/api/parties?eagerload=true"))
            .andExpect(status().isOk());

        verify(partieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPartie() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get the partie
        restPartieMockMvc.perform(get("/api/parties/{id}", partie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partie.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE));
    }


    @Test
    @Transactional
    public void getPartiesByIdFiltering() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        Long id = partie.getId();

        defaultPartieShouldBeFound("id.equals=" + id);
        defaultPartieShouldNotBeFound("id.notEquals=" + id);

        defaultPartieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartieShouldNotBeFound("id.greaterThan=" + id);

        defaultPartieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartieShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartiesByIntituleIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule equals to DEFAULT_INTITULE
        defaultPartieShouldBeFound("intitule.equals=" + DEFAULT_INTITULE);

        // Get all the partieList where intitule equals to UPDATED_INTITULE
        defaultPartieShouldNotBeFound("intitule.equals=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIntituleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule not equals to DEFAULT_INTITULE
        defaultPartieShouldNotBeFound("intitule.notEquals=" + DEFAULT_INTITULE);

        // Get all the partieList where intitule not equals to UPDATED_INTITULE
        defaultPartieShouldBeFound("intitule.notEquals=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIntituleIsInShouldWork() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule in DEFAULT_INTITULE or UPDATED_INTITULE
        defaultPartieShouldBeFound("intitule.in=" + DEFAULT_INTITULE + "," + UPDATED_INTITULE);

        // Get all the partieList where intitule equals to UPDATED_INTITULE
        defaultPartieShouldNotBeFound("intitule.in=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIntituleIsNullOrNotNull() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule is not null
        defaultPartieShouldBeFound("intitule.specified=true");

        // Get all the partieList where intitule is null
        defaultPartieShouldNotBeFound("intitule.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByIntituleContainsSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule contains DEFAULT_INTITULE
        defaultPartieShouldBeFound("intitule.contains=" + DEFAULT_INTITULE);

        // Get all the partieList where intitule contains UPDATED_INTITULE
        defaultPartieShouldNotBeFound("intitule.contains=" + UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void getAllPartiesByIntituleNotContainsSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where intitule does not contain DEFAULT_INTITULE
        defaultPartieShouldNotBeFound("intitule.doesNotContain=" + DEFAULT_INTITULE);

        // Get all the partieList where intitule does not contain UPDATED_INTITULE
        defaultPartieShouldBeFound("intitule.doesNotContain=" + UPDATED_INTITULE);
    }


    @Test
    @Transactional
    public void getAllPartiesByJoueurIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);
        User joueur = UserResourceIT.createEntity(em);
        em.persist(joueur);
        em.flush();
        partie.addJoueur(joueur);
        partieRepository.saveAndFlush(partie);
        Long joueurId = joueur.getId();

        // Get all the partieList where joueur equals to joueurId
        defaultPartieShouldBeFound("joueurId.equals=" + joueurId);

        // Get all the partieList where joueur equals to joueurId + 1
        defaultPartieShouldNotBeFound("joueurId.equals=" + (joueurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartieShouldBeFound(String filter) throws Exception {
        restPartieMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partie.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)));

        // Check, that the count call also returns 1
        restPartieMockMvc.perform(get("/api/parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartieShouldNotBeFound(String filter) throws Exception {
        restPartieMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartieMockMvc.perform(get("/api/parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPartie() throws Exception {
        // Get the partie
        restPartieMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartie() throws Exception {
        // Initialize the database
        partieService.save(partie);

        int databaseSizeBeforeUpdate = partieRepository.findAll().size();

        // Update the partie
        Partie updatedPartie = partieRepository.findById(partie.getId()).get();
        // Disconnect from session so that the updates on updatedPartie are not directly saved in db
        em.detach(updatedPartie);
        updatedPartie
            .intitule(UPDATED_INTITULE);

        restPartieMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartie)))
            .andExpect(status().isOk());

        // Validate the Partie in the database
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeUpdate);
        Partie testPartie = partieList.get(partieList.size() - 1);
        assertThat(testPartie.getIntitule()).isEqualTo(UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartie() throws Exception {
        int databaseSizeBeforeUpdate = partieRepository.findAll().size();

        // Create the Partie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartieMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partie)))
            .andExpect(status().isBadRequest());

        // Validate the Partie in the database
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartie() throws Exception {
        // Initialize the database
        partieService.save(partie);

        int databaseSizeBeforeDelete = partieRepository.findAll().size();

        // Delete the partie
        restPartieMockMvc.perform(delete("/api/parties/{id}", partie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
