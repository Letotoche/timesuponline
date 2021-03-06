package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.domain.Reclamation;
import com.letotoche.timesuponline.service.ReclamationService;
import com.letotoche.timesuponline.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.letotoche.timesuponline.domain.Reclamation}.
 */
@RestController
@RequestMapping("/api")
public class ReclamationResource {

    private final Logger log = LoggerFactory.getLogger(ReclamationResource.class);

    private static final String ENTITY_NAME = "reclamation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReclamationService reclamationService;

    public ReclamationResource(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    /**
     * {@code POST  /reclamations} : Create a new reclamation.
     *
     * @param reclamation the reclamation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reclamation, or with status {@code 400 (Bad Request)} if the reclamation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reclamations")
    public ResponseEntity<Reclamation> createReclamation(@Valid @RequestBody Reclamation reclamation) throws URISyntaxException {
        log.debug("REST request to save Reclamation : {}", reclamation);
        if (reclamation.getId() != null) {
            throw new BadRequestAlertException("A new reclamation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reclamation result = reclamationService.save(reclamation);
        return ResponseEntity.created(new URI("/api/reclamations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reclamations} : Updates an existing reclamation.
     *
     * @param reclamation the reclamation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reclamation,
     * or with status {@code 400 (Bad Request)} if the reclamation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reclamation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reclamations")
    public ResponseEntity<Reclamation> updateReclamation(@Valid @RequestBody Reclamation reclamation) throws URISyntaxException {
        log.debug("REST request to update Reclamation : {}", reclamation);
        if (reclamation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reclamation result = reclamationService.save(reclamation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reclamation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reclamations} : get all the reclamations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reclamations in body.
     */
    @GetMapping("/reclamations")
    public List<Reclamation> getAllReclamations() {
        log.debug("REST request to get all Reclamations");
        return reclamationService.findAll();
    }

    /**
     * {@code GET  /reclamations/:id} : get the "id" reclamation.
     *
     * @param id the id of the reclamation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reclamation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reclamations/{id}")
    public ResponseEntity<Reclamation> getReclamation(@PathVariable Long id) {
        log.debug("REST request to get Reclamation : {}", id);
        Optional<Reclamation> reclamation = reclamationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reclamation);
    }

    /**
     * {@code DELETE  /reclamations/:id} : delete the "id" reclamation.
     *
     * @param id the id of the reclamation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reclamations/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        log.debug("REST request to delete Reclamation : {}", id);
        reclamationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
