package com.dento.care.repository;

import com.dento.care.domain.Appointment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query("select appointment from Appointment appointment where appointment.doctor.login = " +
        "?#{principal.username}")
    List<Appointment> findByUserIsCurrentUser();

    Set<Appointment> findByPatientId(Long patientId);
}
