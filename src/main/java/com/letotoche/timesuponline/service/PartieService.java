package com.letotoche.timesuponline.service;

import com.letotoche.timesuponline.domain.Partie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Partie}.
 */
public interface PartieService {

    /**
     * Save a partie.
     *
     * @param partie the entity to save.
     * @return the persisted entity.
     */
    Partie save(Partie partie);

    /**
     * Get all the parties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Partie> findAll(Pageable pageable);

    /**
     * Get all the parties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Partie> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" partie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Partie> findOne(Long id);

    /**
     * Delete the "id" partie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
