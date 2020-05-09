package com.letotoche.timesuponline.service;

import com.letotoche.timesuponline.domain.Mot;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Mot}.
 */
public interface MotService {

    /**
     * Save a mot.
     *
     * @param mot the entity to save.
     * @return the persisted entity.
     */
    Mot save(Mot mot);

    /**
     * Get all the mots.
     *
     * @return the list of entities.
     */
    List<Mot> findAll();
    /**
     * Get all the MotDTO where Reclamation is {@code null}.
     *
     * @return the list of entities.
     */
    List<Mot> findAllWhereReclamationIsNull();

    /**
     * Get the "id" mot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mot> findOne(Long id);

    /**
     * Delete the "id" mot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
