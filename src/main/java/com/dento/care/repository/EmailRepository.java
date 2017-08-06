package com.dento.care.repository;

import com.dento.care.domain.Email;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {

    Set<Email> findByPatientId(Long patientId);
}
