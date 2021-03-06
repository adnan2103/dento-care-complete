package com.dento.care.repository;

import com.dento.care.domain.Payment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Set<Payment> findByTreatmentId(Long treatmentId);
}
