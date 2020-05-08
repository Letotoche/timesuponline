package com.letotoche.timesuponline.repository;

import com.letotoche.timesuponline.domain.Partie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Partie entity.
 */
@Repository
public interface PartieRepository extends JpaRepository<Partie, Long>, JpaSpecificationExecutor<Partie> {

    @Query(value = "select distinct partie from Partie partie left join fetch partie.joueurs",
        countQuery = "select count(distinct partie) from Partie partie")
    Page<Partie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct partie from Partie partie left join fetch partie.joueurs")
    List<Partie> findAllWithEagerRelationships();

    @Query("select partie from Partie partie left join fetch partie.joueurs where partie.id =:id")
    Optional<Partie> findOneWithEagerRelationships(@Param("id") Long id);
}
