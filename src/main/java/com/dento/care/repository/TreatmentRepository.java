package com.dento.care.repository;

import com.dento.care.domain.Treatment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Treatment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Long> {

    @Query("select treatment from Treatment treatment where treatment.doctor.login = " +
        "?#{principal.username}")
    List<Treatment> findByUserIsCurrentUser();

}
