package com.dento.care.repository;


import com.dento.care.domain.Notes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Notes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotesRepository extends JpaRepository<Notes,Long> {
    Set<Notes> findByTreatmentId(Long treatmentId);
}
