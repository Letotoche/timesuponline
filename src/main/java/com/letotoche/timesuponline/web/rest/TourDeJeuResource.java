package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.domain.TourDeJeu;
import com.letotoche.timesuponline.service.TourDeJeuService;
import com.letotoche.timesuponline.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.letotoche.timesuponline.domain.TourDeJeu}.
 */
@RestController
@RequestMapping("/api")
public class TourDeJeuResource {

    private final Logger log = LoggerFactory.getLogger(TourDeJeuResource.class);

    private static final String ENTITY_NAME = "tourDeJeu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourDeJeuService tourDeJeuService;

    public TourDeJeuResource(TourDeJeuService tourDeJeuService) {
        this.tourDeJeuService = tourDeJeuService;
    }

    /**
     * {@code POST  /tour-de-jeus} : Create a new tourDeJeu.
     *
     * @param tourDeJeu the tourDeJeu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourDeJeu, or with status {@code 400 (Bad Request)} if the tourDeJeu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tour-de-jeus")
    public ResponseEntity<TourDeJeu> createTourDeJeu(@RequestBody TourDeJeu tourDeJeu) throws URISyntaxException {
        log.debug("REST request to save TourDeJeu : {}", tourDeJeu);
        if (tourDeJeu.getId() != null) {
            throw new BadRequestAlertException("A new tourDeJeu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TourDeJeu result = tourDeJeuService.save(tourDeJeu);
        return ResponseEntity.created(new URI("/api/tour-de-jeus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tour-de-jeus} : Updates an existing tourDeJeu.
     *
     * @param tourDeJeu the tourDeJeu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourDeJeu,
     * or with status {@code 400 (Bad Request)} if the tourDeJeu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourDeJeu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tour-de-jeus")
    public ResponseEntity<TourDeJeu> updateTourDeJeu(@RequestBody TourDeJeu tourDeJeu) throws URISyntaxException {
        log.debug("REST request to update TourDeJeu : {}", tourDeJeu);
        if (tourDeJeu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TourDeJeu result = tourDeJeuService.save(tourDeJeu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourDeJeu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tour-de-jeus} : get all the tourDeJeus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourDeJeus in body.
     */
    @GetMapping("/tour-de-jeus")
    public List<TourDeJeu> getAllTourDeJeus() {
        log.debug("REST request to get all TourDeJeus");
        return tourDeJeuService.findAll();
    }

    /**
     * {@code GET  /tour-de-jeus/:id} : get the "id" tourDeJeu.
     *
     * @param id the id of the tourDeJeu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourDeJeu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tour-de-jeus/{id}")
    public ResponseEntity<TourDeJeu> getTourDeJeu(@PathVariable Long id) {
        log.debug("REST request to get TourDeJeu : {}", id);
        Optional<TourDeJeu> tourDeJeu = tourDeJeuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourDeJeu);
    }

    /**
     * {@code DELETE  /tour-de-jeus/:id} : delete the "id" tourDeJeu.
     *
     * @param id the id of the tourDeJeu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tour-de-jeus/{id}")
    public ResponseEntity<Void> deleteTourDeJeu(@PathVariable Long id) {
        log.debug("REST request to delete TourDeJeu : {}", id);
        tourDeJeuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
