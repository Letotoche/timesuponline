package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.domain.Mot;
import com.letotoche.timesuponline.service.MotService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.letotoche.timesuponline.domain.Mot}.
 */
@RestController
@RequestMapping("/api")
public class MotResource {

    private final Logger log = LoggerFactory.getLogger(MotResource.class);

    private static final String ENTITY_NAME = "mot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotService motService;

    public MotResource(MotService motService) {
        this.motService = motService;
    }

    /**
     * {@code POST  /mots} : Create a new mot.
     *
     * @param mot the mot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mot, or with status {@code 400 (Bad Request)} if the mot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mots")
    public ResponseEntity<Mot> createMot(@Valid @RequestBody Mot mot) throws URISyntaxException {
        log.debug("REST request to save Mot : {}", mot);
        if (mot.getId() != null) {
            throw new BadRequestAlertException("A new mot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mot result = motService.save(mot);
        return ResponseEntity.created(new URI("/api/mots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mots} : Updates an existing mot.
     *
     * @param mot the mot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mot,
     * or with status {@code 400 (Bad Request)} if the mot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mots")
    public ResponseEntity<Mot> updateMot(@Valid @RequestBody Mot mot) throws URISyntaxException {
        log.debug("REST request to update Mot : {}", mot);
        if (mot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mot result = motService.save(mot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mot.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mots} : get all the mots.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mots in body.
     */
    @GetMapping("/mots")
    public List<Mot> getAllMots(@RequestParam(required = false) String filter) {
        if ("reclamation-is-null".equals(filter)) {
            log.debug("REST request to get all Mots where reclamation is null");
            return motService.findAllWhereReclamationIsNull();
        }
        log.debug("REST request to get all Mots");
        return motService.findAll();
    }

    /**
     * {@code GET  /mots/:id} : get the "id" mot.
     *
     * @param id the id of the mot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mots/{id}")
    public ResponseEntity<Mot> getMot(@PathVariable Long id) {
        log.debug("REST request to get Mot : {}", id);
        Optional<Mot> mot = motService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mot);
    }

    /**
     * {@code DELETE  /mots/:id} : delete the "id" mot.
     *
     * @param id the id of the mot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mots/{id}")
    public ResponseEntity<Void> deleteMot(@PathVariable Long id) {
        log.debug("REST request to delete Mot : {}", id);
        motService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
