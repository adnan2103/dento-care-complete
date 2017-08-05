package com.dento.care.repository;

import com.dento.care.domain.Patient;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("select patient from Patient patient where patient.doctor.login = " +
        "?#{principal.username}")
    List<Patient> findByUserIsCurrentUser();

}
