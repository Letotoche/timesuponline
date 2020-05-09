package com.letotoche.timesuponline.web.rest;

import com.letotoche.timesuponline.domain.Partie;
import com.letotoche.timesuponline.service.PartieService;
import com.letotoche.timesuponline.web.rest.errors.BadRequestAlertException;
import com.letotoche.timesuponline.service.dto.PartieCriteria;
import com.letotoche.timesuponline.service.PartieQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.letotoche.timesuponline.domain.Partie}.
 */
@RestController
@RequestMapping("/api")
public class PartieResource {

    private final Logger log = LoggerFactory.getLogger(PartieResource.class);

    private static final String ENTITY_NAME = "partie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartieService partieService;

    private final PartieQueryService partieQueryService;

    public PartieResource(PartieService partieService, PartieQueryService partieQueryService) {
        this.partieService = partieService;
        this.partieQueryService = partieQueryService;
    }

    /**
     * {@code POST  /parties} : Create a new partie.
     *
     * @param partie the partie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partie, or with status {@code 400 (Bad Request)} if the partie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parties")
    public ResponseEntity<Partie> createPartie(@Valid @RequestBody Partie partie) throws URISyntaxException {
        log.debug("REST request to save Partie : {}", partie);
        if (partie.getId() != null) {
            throw new BadRequestAlertException("A new partie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partie result = partieService.save(partie);
        return ResponseEntity.created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parties} : Updates an existing partie.
     *
     * @param partie the partie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partie,
     * or with status {@code 400 (Bad Request)} if the partie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parties")
    public ResponseEntity<Partie> updatePartie(@Valid @RequestBody Partie partie) throws URISyntaxException {
        log.debug("REST request to update Partie : {}", partie);
        if (partie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Partie result = partieService.save(partie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parties} : get all the parties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parties in body.
     */
    @GetMapping("/parties")
    public ResponseEntity<List<Partie>> getAllParties(PartieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Parties by criteria: {}", criteria);
        Page<Partie> page = partieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parties/count} : count all the parties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parties/count")
    public ResponseEntity<Long> countParties(PartieCriteria criteria) {
        log.debug("REST request to count Parties by criteria: {}", criteria);
        return ResponseEntity.ok().body(partieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parties/:id} : get the "id" partie.
     *
     * @param id the id of the partie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parties/{id}")
    public ResponseEntity<Partie> getPartie(@PathVariable Long id) {
        log.debug("REST request to get Partie : {}", id);
        Optional<Partie> partie = partieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partie);
    }

    /**
     * {@code DELETE  /parties/:id} : delete the "id" partie.
     *
     * @param id the id of the partie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parties/{id}")
    public ResponseEntity<Void> deletePartie(@PathVariable Long id) {
        log.debug("REST request to delete Partie : {}", id);
        partieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
