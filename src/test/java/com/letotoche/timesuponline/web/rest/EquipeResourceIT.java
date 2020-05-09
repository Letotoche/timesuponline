package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.Equipe;
import com.letotoche.timesuponline.repository.EquipeRepository;
import com.letotoche.timesuponline.service.EquipeService;

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
 * Integration tests for the {@link EquipeResource} REST controller.
 */
@SpringBootTest(classes = TimesuponlineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquipeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE_1 = 1;
    private static final Integer UPDATED_SCORE_1 = 2;

    private static final Integer DEFAULT_SCORE_2 = 1;
    private static final Integer UPDATED_SCORE_2 = 2;

    private static final Integer DEFAULT_SCORE_3 = 1;
    private static final Integer UPDATED_SCORE_3 = 2;

    @Autowired
    private EquipeRepository equipeRepository;

    @Mock
    private EquipeRepository equipeRepositoryMock;

    @Mock
    private EquipeService equipeServiceMock;

    @Autowired
    private EquipeService equipeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .nom(DEFAULT_NOM)
            .score1(DEFAULT_SCORE_1)
            .score2(DEFAULT_SCORE_2)
            .score3(DEFAULT_SCORE_3);
        return equipe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createUpdatedEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .nom(UPDATED_NOM)
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2)
            .score3(UPDATED_SCORE_3);
        return equipe;
    }

    @BeforeEach
    public void initTest() {
        equipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipe() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // Create the Equipe
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isCreated());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate + 1);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEquipe.getScore1()).isEqualTo(DEFAULT_SCORE_1);
        assertThat(testEquipe.getScore2()).isEqualTo(DEFAULT_SCORE_2);
        assertThat(testEquipe.getScore3()).isEqualTo(DEFAULT_SCORE_3);
    }

    @Test
    @Transactional
    public void createEquipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // Create the Equipe with an existing ID
        equipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipeRepository.findAll().size();
        // set the field null
        equipe.setNom(null);

        // Create the Equipe, which fails.

        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipes() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc.perform(get("/api/equipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].score1").value(hasItem(DEFAULT_SCORE_1)))
            .andExpect(jsonPath("$.[*].score2").value(hasItem(DEFAULT_SCORE_2)))
            .andExpect(jsonPath("$.[*].score3").value(hasItem(DEFAULT_SCORE_3)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEquipesWithEagerRelationshipsIsEnabled() throws Exception {
        when(equipeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEquipeMockMvc.perform(get("/api/equipes?eagerload=true"))
            .andExpect(status().isOk());

        verify(equipeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEquipesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(equipeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEquipeMockMvc.perform(get("/api/equipes?eagerload=true"))
            .andExpect(status().isOk());

        verify(equipeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.score1").value(DEFAULT_SCORE_1))
            .andExpect(jsonPath("$.score2").value(DEFAULT_SCORE_2))
            .andExpect(jsonPath("$.score3").value(DEFAULT_SCORE_3));
    }

    @Test
    @Transactional
    public void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipe() throws Exception {
        // Initialize the database
        equipeService.save(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).get();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe
            .nom(UPDATED_NOM)
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2)
            .score3(UPDATED_SCORE_3);

        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipe)))
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEquipe.getScore1()).isEqualTo(UPDATED_SCORE_1);
        assertThat(testEquipe.getScore2()).isEqualTo(UPDATED_SCORE_2);
        assertThat(testEquipe.getScore3()).isEqualTo(UPDATED_SCORE_3);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Create the Equipe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipe() throws Exception {
        // Initialize the database
        equipeService.save(equipe);

        int databaseSizeBeforeDelete = equipeRepository.findAll().size();

        // Delete the equipe
        restEquipeMockMvc.perform(delete("/api/equipes/{id}", equipe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
