package com.dento.care.repository;

import com.dento.care.domain.PreTreatmentImage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the PreTreatmentImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreTreatmentImageRepository extends JpaRepository<PreTreatmentImage,Long> {
    Set<PreTreatmentImage> findByTreatmentId(Long treatmentId);
}
