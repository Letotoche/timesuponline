package com.letotoche.timesuponline.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.letotoche.timesuponline.domain.Partie;
import com.letotoche.timesuponline.domain.*; // for static metamodels
import com.letotoche.timesuponline.repository.PartieRepository;
import com.letotoche.timesuponline.service.dto.PartieCriteria;

/**
 * Service for executing complex queries for {@link Partie} entities in the database.
 * The main input is a {@link PartieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Partie} or a {@link Page} of {@link Partie} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartieQueryService extends QueryService<Partie> {

    private final Logger log = LoggerFactory.getLogger(PartieQueryService.class);

    private final PartieRepository partieRepository;

    public PartieQueryService(PartieRepository partieRepository) {
        this.partieRepository = partieRepository;
    }

    /**
     * Return a {@link List} of {@link Partie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Partie> findByCriteria(PartieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Partie> specification = createSpecification(criteria);
        return partieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Partie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Partie> findByCriteria(PartieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Partie> specification = createSpecification(criteria);
        return partieRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Partie> specification = createSpecification(criteria);
        return partieRepository.count(specification);
    }

    /**
     * Function to convert {@link PartieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Partie> createSpecification(PartieCriteria criteria) {
        Specification<Partie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Partie_.id));
            }
            if (criteria.getIntitule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntitule(), Partie_.intitule));
            }
            if (criteria.getJoueurId() != null) {
                specification = specification.and(buildSpecification(criteria.getJoueurId(),
                    root -> root.join(Partie_.joueurs, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
