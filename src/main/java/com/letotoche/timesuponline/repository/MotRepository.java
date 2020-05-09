package com.letotoche.timesuponline.repository;

import com.letotoche.timesuponline.domain.Mot;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotRepository extends JpaRepository<Mot, Long> {
}
