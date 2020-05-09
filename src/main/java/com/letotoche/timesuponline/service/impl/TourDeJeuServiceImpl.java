package com.letotoche.timesuponline.service.impl;

import com.letotoche.timesuponline.service.TourDeJeuService;
import com.letotoche.timesuponline.domain.TourDeJeu;
import com.letotoche.timesuponline.repository.TourDeJeuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TourDeJeu}.
 */
@Service
@Transactional
public class TourDeJeuServiceImpl implements TourDeJeuService {

    private final Logger log = LoggerFactory.getLogger(TourDeJeuServiceImpl.class);

    private final TourDeJeuRepository tourDeJeuRepository;

    public TourDeJeuServiceImpl(TourDeJeuRepository tourDeJeuRepository) {
        this.tourDeJeuRepository = tourDeJeuRepository;
    }

    /**
     * Save a tourDeJeu.
     *
     * @param tourDeJeu the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TourDeJeu save(TourDeJeu tourDeJeu) {
        log.debug("Request to save TourDeJeu : {}", tourDeJeu);
        return tourDeJeuRepository.save(tourDeJeu);
    }

    /**
     * Get all the tourDeJeus.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TourDeJeu> findAll() {
        log.debug("Request to get all TourDeJeus");
        return tourDeJeuRepository.findAll();
    }

    /**
     * Get one tourDeJeu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TourDeJeu> findOne(Long id) {
        log.debug("Request to get TourDeJeu : {}", id);
        return tourDeJeuRepository.findById(id);
    }

    /**
     * Delete the tourDeJeu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourDeJeu : {}", id);
        tourDeJeuRepository.deleteById(id);
    }
}
