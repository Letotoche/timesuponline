package com.letotoche.timesuponline.service;

import com.letotoche.timesuponline.domain.TourDeJeu;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TourDeJeu}.
 */
public interface TourDeJeuService {

    /**
     * Save a tourDeJeu.
     *
     * @param tourDeJeu the entity to save.
     * @return the persisted entity.
     */
    TourDeJeu save(TourDeJeu tourDeJeu);

    /**
     * Get all the tourDeJeus.
     *
     * @return the list of entities.
     */
    List<TourDeJeu> findAll();

    /**
     * Get the "id" tourDeJeu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourDeJeu> findOne(Long id);

    /**
     * Delete the "id" tourDeJeu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
