package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.TimesuponlineApp;
import com.letotoche.timesuponline.domain.Partie;
import com.letotoche.timesuponline.domain.Equipe;
import com.letotoche.timesuponline.domain.User;
import com.letotoche.timesuponline.domain.Mot;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.letotoche.timesuponline.domain.enumeration.PhasePartie;
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

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_CREATION = LocalDate.ofEpochDay(-1L);

    private static final PhasePartie DEFAULT_PHASE = PhasePartie.CREEE;
    private static final PhasePartie UPDATED_PHASE = PhasePartie.RECRUTEMENT;

    private static final Integer DEFAULT_NB_MOTS = 1;
    private static final Integer UPDATED_NB_MOTS = 2;
    private static final Integer SMALLER_NB_MOTS = 1 - 1;

    private static final Integer DEFAULT_TEMPS_SABLIER = 1;
    private static final Integer UPDATED_TEMPS_SABLIER = 2;
    private static final Integer SMALLER_TEMPS_SABLIER = 1 - 1;

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
            .intitule(DEFAULT_INTITULE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .phase(DEFAULT_PHASE)
            .nbMots(DEFAULT_NB_MOTS)
            .tempsSablier(DEFAULT_TEMPS_SABLIER);
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
            .intitule(UPDATED_INTITULE)
            .dateCreation(UPDATED_DATE_CREATION)
            .phase(UPDATED_PHASE)
            .nbMots(UPDATED_NB_MOTS)
            .tempsSablier(UPDATED_TEMPS_SABLIER);
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
        assertThat(testPartie.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPartie.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testPartie.getNbMots()).isEqualTo(DEFAULT_NB_MOTS);
        assertThat(testPartie.getTempsSablier()).isEqualTo(DEFAULT_TEMPS_SABLIER);
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
    public void checkPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = partieRepository.findAll().size();
        // set the field null
        partie.setPhase(null);

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
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].nbMots").value(hasItem(DEFAULT_NB_MOTS)))
            .andExpect(jsonPath("$.[*].tempsSablier").value(hasItem(DEFAULT_TEMPS_SABLIER)));
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
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.nbMots").value(DEFAULT_NB_MOTS))
            .andExpect(jsonPath("$.tempsSablier").value(DEFAULT_TEMPS_SABLIER));
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
    public void getAllPartiesByDateCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation equals to DEFAULT_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.equals=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation equals to UPDATED_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.equals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation not equals to DEFAULT_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.notEquals=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation not equals to UPDATED_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.notEquals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsInShouldWork() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation in DEFAULT_DATE_CREATION or UPDATED_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.in=" + DEFAULT_DATE_CREATION + "," + UPDATED_DATE_CREATION);

        // Get all the partieList where dateCreation equals to UPDATED_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.in=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation is not null
        defaultPartieShouldBeFound("dateCreation.specified=true");

        // Get all the partieList where dateCreation is null
        defaultPartieShouldNotBeFound("dateCreation.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation is greater than or equal to DEFAULT_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.greaterThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation is greater than or equal to UPDATED_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.greaterThanOrEqual=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation is less than or equal to DEFAULT_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.lessThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation is less than or equal to SMALLER_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.lessThanOrEqual=" + SMALLER_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsLessThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation is less than DEFAULT_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.lessThan=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation is less than UPDATED_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.lessThan=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateCreationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where dateCreation is greater than DEFAULT_DATE_CREATION
        defaultPartieShouldNotBeFound("dateCreation.greaterThan=" + DEFAULT_DATE_CREATION);

        // Get all the partieList where dateCreation is greater than SMALLER_DATE_CREATION
        defaultPartieShouldBeFound("dateCreation.greaterThan=" + SMALLER_DATE_CREATION);
    }


    @Test
    @Transactional
    public void getAllPartiesByPhaseIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where phase equals to DEFAULT_PHASE
        defaultPartieShouldBeFound("phase.equals=" + DEFAULT_PHASE);

        // Get all the partieList where phase equals to UPDATED_PHASE
        defaultPartieShouldNotBeFound("phase.equals=" + UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPhaseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where phase not equals to DEFAULT_PHASE
        defaultPartieShouldNotBeFound("phase.notEquals=" + DEFAULT_PHASE);

        // Get all the partieList where phase not equals to UPDATED_PHASE
        defaultPartieShouldBeFound("phase.notEquals=" + UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPhaseIsInShouldWork() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where phase in DEFAULT_PHASE or UPDATED_PHASE
        defaultPartieShouldBeFound("phase.in=" + DEFAULT_PHASE + "," + UPDATED_PHASE);

        // Get all the partieList where phase equals to UPDATED_PHASE
        defaultPartieShouldNotBeFound("phase.in=" + UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPhaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where phase is not null
        defaultPartieShouldBeFound("phase.specified=true");

        // Get all the partieList where phase is null
        defaultPartieShouldNotBeFound("phase.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots equals to DEFAULT_NB_MOTS
        defaultPartieShouldBeFound("nbMots.equals=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots equals to UPDATED_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.equals=" + UPDATED_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots not equals to DEFAULT_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.notEquals=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots not equals to UPDATED_NB_MOTS
        defaultPartieShouldBeFound("nbMots.notEquals=" + UPDATED_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsInShouldWork() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots in DEFAULT_NB_MOTS or UPDATED_NB_MOTS
        defaultPartieShouldBeFound("nbMots.in=" + DEFAULT_NB_MOTS + "," + UPDATED_NB_MOTS);

        // Get all the partieList where nbMots equals to UPDATED_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.in=" + UPDATED_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsNullOrNotNull() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots is not null
        defaultPartieShouldBeFound("nbMots.specified=true");

        // Get all the partieList where nbMots is null
        defaultPartieShouldNotBeFound("nbMots.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots is greater than or equal to DEFAULT_NB_MOTS
        defaultPartieShouldBeFound("nbMots.greaterThanOrEqual=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots is greater than or equal to UPDATED_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.greaterThanOrEqual=" + UPDATED_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots is less than or equal to DEFAULT_NB_MOTS
        defaultPartieShouldBeFound("nbMots.lessThanOrEqual=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots is less than or equal to SMALLER_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.lessThanOrEqual=" + SMALLER_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsLessThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots is less than DEFAULT_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.lessThan=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots is less than UPDATED_NB_MOTS
        defaultPartieShouldBeFound("nbMots.lessThan=" + UPDATED_NB_MOTS);
    }

    @Test
    @Transactional
    public void getAllPartiesByNbMotsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where nbMots is greater than DEFAULT_NB_MOTS
        defaultPartieShouldNotBeFound("nbMots.greaterThan=" + DEFAULT_NB_MOTS);

        // Get all the partieList where nbMots is greater than SMALLER_NB_MOTS
        defaultPartieShouldBeFound("nbMots.greaterThan=" + SMALLER_NB_MOTS);
    }


    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier equals to DEFAULT_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.equals=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier equals to UPDATED_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.equals=" + UPDATED_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier not equals to DEFAULT_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.notEquals=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier not equals to UPDATED_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.notEquals=" + UPDATED_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsInShouldWork() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier in DEFAULT_TEMPS_SABLIER or UPDATED_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.in=" + DEFAULT_TEMPS_SABLIER + "," + UPDATED_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier equals to UPDATED_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.in=" + UPDATED_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsNullOrNotNull() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier is not null
        defaultPartieShouldBeFound("tempsSablier.specified=true");

        // Get all the partieList where tempsSablier is null
        defaultPartieShouldNotBeFound("tempsSablier.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier is greater than or equal to DEFAULT_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.greaterThanOrEqual=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier is greater than or equal to UPDATED_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.greaterThanOrEqual=" + UPDATED_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier is less than or equal to DEFAULT_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.lessThanOrEqual=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier is less than or equal to SMALLER_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.lessThanOrEqual=" + SMALLER_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsLessThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier is less than DEFAULT_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.lessThan=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier is less than UPDATED_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.lessThan=" + UPDATED_TEMPS_SABLIER);
    }

    @Test
    @Transactional
    public void getAllPartiesByTempsSablierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);

        // Get all the partieList where tempsSablier is greater than DEFAULT_TEMPS_SABLIER
        defaultPartieShouldNotBeFound("tempsSablier.greaterThan=" + DEFAULT_TEMPS_SABLIER);

        // Get all the partieList where tempsSablier is greater than SMALLER_TEMPS_SABLIER
        defaultPartieShouldBeFound("tempsSablier.greaterThan=" + SMALLER_TEMPS_SABLIER);
    }


    @Test
    @Transactional
    public void getAllPartiesByEquipeIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);
        Equipe equipe = EquipeResourceIT.createEntity(em);
        em.persist(equipe);
        em.flush();
        partie.addEquipe(equipe);
        partieRepository.saveAndFlush(partie);
        Long equipeId = equipe.getId();

        // Get all the partieList where equipe equals to equipeId
        defaultPartieShouldBeFound("equipeId.equals=" + equipeId);

        // Get all the partieList where equipe equals to equipeId + 1
        defaultPartieShouldNotBeFound("equipeId.equals=" + (equipeId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);
        User master = UserResourceIT.createEntity(em);
        em.persist(master);
        em.flush();
        partie.setMaster(master);
        partieRepository.saveAndFlush(partie);
        Long masterId = master.getId();

        // Get all the partieList where master equals to masterId
        defaultPartieShouldBeFound("masterId.equals=" + masterId);

        // Get all the partieList where master equals to masterId + 1
        defaultPartieShouldNotBeFound("masterId.equals=" + (masterId + 1));
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


    @Test
    @Transactional
    public void getAllPartiesByPaquetIsEqualToSomething() throws Exception {
        // Initialize the database
        partieRepository.saveAndFlush(partie);
        Mot paquet = MotResourceIT.createEntity(em);
        em.persist(paquet);
        em.flush();
        partie.addPaquet(paquet);
        partieRepository.saveAndFlush(partie);
        Long paquetId = paquet.getId();

        // Get all the partieList where paquet equals to paquetId
        defaultPartieShouldBeFound("paquetId.equals=" + paquetId);

        // Get all the partieList where paquet equals to paquetId + 1
        defaultPartieShouldNotBeFound("paquetId.equals=" + (paquetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartieShouldBeFound(String filter) throws Exception {
        restPartieMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partie.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
            .andExpect(jsonPath("$.[*].nbMots").value(hasItem(DEFAULT_NB_MOTS)))
            .andExpect(jsonPath("$.[*].tempsSablier").value(hasItem(DEFAULT_TEMPS_SABLIER)));

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
            .intitule(UPDATED_INTITULE)
            .dateCreation(UPDATED_DATE_CREATION)
            .phase(UPDATED_PHASE)
            .nbMots(UPDATED_NB_MOTS)
            .tempsSablier(UPDATED_TEMPS_SABLIER);

        restPartieMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartie)))
            .andExpect(status().isOk());

        // Validate the Partie in the database
        List<Partie> partieList = partieRepository.findAll();
        assertThat(partieList).hasSize(databaseSizeBeforeUpdate);
        Partie testPartie = partieList.get(partieList.size() - 1);
        assertThat(testPartie.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testPartie.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPartie.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testPartie.getNbMots()).isEqualTo(UPDATED_NB_MOTS);
        assertThat(testPartie.getTempsSablier()).isEqualTo(UPDATED_TEMPS_SABLIER);
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
