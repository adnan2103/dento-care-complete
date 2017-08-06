package com.dento.care.repository;

import com.dento.care.domain.OralExamination;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the OralExamination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OralExaminationRepository extends JpaRepository<OralExamination,Long> {
    Set<OralExamination> findByTreatmentId(Long treatmentId);
}
