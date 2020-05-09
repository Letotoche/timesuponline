package com.letotoche.timesuponline.repository;

import com.letotoche.timesuponline.domain.Equipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Equipe entity.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query(value = "select distinct equipe from Equipe equipe left join fetch equipe.membres",
        countQuery = "select count(distinct equipe) from Equipe equipe")
    Page<Equipe> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct equipe from Equipe equipe left join fetch equipe.membres")
    List<Equipe> findAllWithEagerRelationships();

    @Query("select equipe from Equipe equipe left join fetch equipe.membres where equipe.id =:id")
    Optional<Equipe> findOneWithEagerRelationships(@Param("id") Long id);
}
