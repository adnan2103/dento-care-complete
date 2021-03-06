package com.dento.care.repository;

import com.dento.care.domain.PostTreatmentImage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the PostTreatmentImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostTreatmentImageRepository extends JpaRepository<PostTreatmentImage,Long> {
    Set<PostTreatmentImage> findByTreatmentId(Long treatmentId);
}
