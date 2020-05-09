package com.letotoche.timesuponline.service.impl;

import com.letotoche.timesuponline.service.MotService;
import com.letotoche.timesuponline.domain.Mot;
import com.letotoche.timesuponline.repository.MotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Mot}.
 */
@Service
@Transactional
public class MotServiceImpl implements MotService {

    private final Logger log = LoggerFactory.getLogger(MotServiceImpl.class);

    private final MotRepository motRepository;

    public MotServiceImpl(MotRepository motRepository) {
        this.motRepository = motRepository;
    }

    /**
     * Save a mot.
     *
     * @param mot the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Mot save(Mot mot) {
        log.debug("Request to save Mot : {}", mot);
        return motRepository.save(mot);
    }

    /**
     * Get all the mots.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Mot> findAll() {
        log.debug("Request to get all Mots");
        return motRepository.findAll();
    }


    /**
     *  Get all the mots where Reclamation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Mot> findAllWhereReclamationIsNull() {
        log.debug("Request to get all mots where Reclamation is null");
        return StreamSupport
            .stream(motRepository.findAll().spliterator(), false)
            .filter(mot -> mot.getReclamation() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one mot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Mot> findOne(Long id) {
        log.debug("Request to get Mot : {}", id);
        return motRepository.findById(id);
    }

    /**
     * Delete the mot by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mot : {}", id);
        motRepository.deleteById(id);
    }
}
