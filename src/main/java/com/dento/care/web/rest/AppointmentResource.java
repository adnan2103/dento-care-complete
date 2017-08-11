package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Appointment;

import com.dento.care.domain.User;
import com.dento.care.repository.AppointmentRepository;
import com.dento.care.repository.PatientRepository;
import com.dento.care.repository.UserRepository;
import com.dento.care.security.SecurityUtils;
import com.dento.care.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Appointment.
 */
@RestController
@RequestMapping("/api")
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    private final AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    public AppointmentResource(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * POST  /appointments : Create a new appointment for the given patient.
     *
     * @param appointment the appointment to create
     * @param patientId Patient Id whose appointment to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new appointment, or with status 400 (Bad Request) if the appointment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients/{patientId}/appointments")
    @Timed
    public ResponseEntity<Appointment> createAppointmentForPatient(@RequestBody Appointment appointment,
                                                         @PathVariable Long patientId ) throws URISyntaxException {
        log.debug("REST request to save Appointment : {}", appointment);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        appointment.setDoctor(user.get());

        appointment.setPatient(patientRepository.getOne(patientId));

        if (appointment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appointment cannot already have an ID")).body(null);
        }
        Appointment result = appointmentRepository.save(appointment);
        return ResponseEntity.created(new URI("/api/patients/"+patientId+"/appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appointments : Updates an existing appointment for given patient.
     *
     * @param appointment the appointment to update
     * @param patientId Patient Id whose appointment to be created.
     * @return the ResponseEntity with status 200 (OK) and with body the updated appointment,
     * or with status 400 (Bad Request) if the appointment is not valid,
     * or with status 500 (Internal Server Error) if the appointment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients/{patientId}/appointments")
    @Timed
    public ResponseEntity<Appointment> updateAppointmentForPatinet(@RequestBody Appointment appointment,
                                                         @PathVariable Long patientId) throws URISyntaxException {
        log.debug("REST request to update Appointment : {}", appointment);
        if (appointment.getId() == null) {
            return createAppointmentForPatient(appointment, patientId);
        }
        appointment.setPatient(patientRepository.getOne(patientId));

        //@TODO extract it as function.
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        appointment.setDoctor(user.get());

        Appointment result = appointmentRepository.save(appointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appointment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appointments : get all the appointments of logged in doctor.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appointments in body
     */
    @GetMapping("/appointments")
    @Timed
    public List<Appointment> getAllAppointmentsOfLoggedInDoctor() {
        log.debug("REST request to get all Appointments");
        return appointmentRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  patients:id/appointments : get all appointment of a patient.
     *
     * @param patientId the id of the patient whose appointment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointments, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{patientId}/appointments")
    @Timed
    public Set<Appointment> getPatientsAllAppointment(@PathVariable Long patientId) {
        log.debug("REST request to get Patient : {} all appointments", patientId);
        return appointmentRepository.findByPatientId(patientId);

    }

    /**
     * GET  /appointments/:id : get the "id" appointment.
     *
     * @param id the id of the appointment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointment, or with status 404 (Not Found)
     */
    @GetMapping("/appointments/{id}")
    @Timed
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        log.debug("REST request to get Appointment : {}", id);
        Appointment appointment = appointmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appointment));
    }

    /**
     * DELETE  /appointments/:id : delete the "id" appointment.
     *
     * @param id the id of the appointment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appointments/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        log.debug("REST request to delete Appointment : {}", id);
        appointmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
