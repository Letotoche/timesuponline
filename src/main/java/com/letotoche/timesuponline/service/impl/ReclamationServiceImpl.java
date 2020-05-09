package com.letotoche.timesuponline.service.impl;

import com.letotoche.timesuponline.service.ReclamationService;
import com.letotoche.timesuponline.domain.Reclamation;
import com.letotoche.timesuponline.repository.ReclamationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Reclamation}.
 */
@Service
@Transactional
public class ReclamationServiceImpl implements ReclamationService {

    private final Logger log = LoggerFactory.getLogger(ReclamationServiceImpl.class);

    private final ReclamationRepository reclamationRepository;

    public ReclamationServiceImpl(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    /**
     * Save a reclamation.
     *
     * @param reclamation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Reclamation save(Reclamation reclamation) {
        log.debug("Request to save Reclamation : {}", reclamation);
        return reclamationRepository.save(reclamation);
    }

    /**
     * Get all the reclamations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reclamation> findAll() {
        log.debug("Request to get all Reclamations");
        return reclamationRepository.findAll();
    }

    /**
     * Get one reclamation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reclamation> findOne(Long id) {
        log.debug("Request to get Reclamation : {}", id);
        return reclamationRepository.findById(id);
    }

    /**
     * Delete the reclamation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reclamation : {}", id);
        reclamationRepository.deleteById(id);
    }
}
