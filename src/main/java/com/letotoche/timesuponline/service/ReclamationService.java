package com.letotoche.timesuponline.service;

import com.letotoche.timesuponline.domain.Reclamation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Reclamation}.
 */
public interface ReclamationService {

    /**
     * Save a reclamation.
     *
     * @param reclamation the entity to save.
     * @return the persisted entity.
     */
    Reclamation save(Reclamation reclamation);

    /**
     * Get all the reclamations.
     *
     * @return the list of entities.
     */
    List<Reclamation> findAll();

    /**
     * Get the "id" reclamation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reclamation> findOne(Long id);

    /**
     * Delete the "id" reclamation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
