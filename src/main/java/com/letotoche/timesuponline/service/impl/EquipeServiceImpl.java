package com.letotoche.timesuponline.service.impl;

import com.letotoche.timesuponline.service.EquipeService;
import com.letotoche.timesuponline.domain.Equipe;
import com.letotoche.timesuponline.repository.EquipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Equipe}.
 */
@Service
@Transactional
public class EquipeServiceImpl implements EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeRepository equipeRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    /**
     * Save a equipe.
     *
     * @param equipe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Equipe save(Equipe equipe) {
        log.debug("Request to save Equipe : {}", equipe);
        return equipeRepository.save(equipe);
    }

    /**
     * Get all the equipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Equipe> findAll(Pageable pageable) {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll(pageable);
    }

    /**
     * Get all the equipes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Equipe> findAllWithEagerRelationships(Pageable pageable) {
        return equipeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one equipe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Equipe> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the equipe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
    }
}
