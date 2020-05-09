package com.letotoche.timesuponline.repository;

import com.letotoche.timesuponline.domain.TourDeJeu;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TourDeJeu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TourDeJeuRepository extends JpaRepository<TourDeJeu, Long> {
}
