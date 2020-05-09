package com.letotoche.timesuponline.repository;

import com.letotoche.timesuponline.domain.Reclamation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reclamation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
