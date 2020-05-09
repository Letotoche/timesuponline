package com.letotoche.timesuponline.service.impl;

import com.letotoche.timesuponline.service.PartieService;
import com.letotoche.timesuponline.domain.Partie;
import com.letotoche.timesuponline.repository.PartieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Partie}.
 */
@Service
@Transactional
public class PartieServiceImpl implements PartieService {

    private final Logger log = LoggerFactory.getLogger(PartieServiceImpl.class);

    private final PartieRepository partieRepository;

    public PartieServiceImpl(PartieRepository partieRepository) {
        this.partieRepository = partieRepository;
    }

    /**
     * Save a partie.
     *
     * @param partie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Partie save(Partie partie) {
        log.debug("Request to save Partie : {}", partie);
        return partieRepository.save(partie);
    }

    /**
     * Get all the parties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Partie> findAll(Pageable pageable) {
        log.debug("Request to get all Parties");
        return partieRepository.findAll(pageable);
    }

    /**
     * Get all the parties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Partie> findAllWithEagerRelationships(Pageable pageable) {
        return partieRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one partie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Partie> findOne(Long id) {
        log.debug("Request to get Partie : {}", id);
        return partieRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the partie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Partie : {}", id);
        partieRepository.deleteById(id);
    }
}
