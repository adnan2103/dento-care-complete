package com.dento.care.repository;

import com.dento.care.domain.ContactNumber;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the ContactNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactNumberRepository extends JpaRepository<ContactNumber,Long> {

    Set<ContactNumber> findByPatientId(Long patientId);
}
