package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Treatment;

import com.dento.care.domain.User;
import com.dento.care.repository.*;
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

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);

    private static final String ENTITY_NAME = "treatment";

    private final TreatmentRepository treatmentRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OralExaminationRepository oralExaminationRepository;

    @Autowired
    private PreTreatmentImageRepository preTreatmentImageRepository;

    @Autowired
    private PostTreatmentImageRepository postTreatmentImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    public TreatmentResource(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    private void setDoctor(Treatment treatment) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        treatment.setDoctor(user.get());
    }

    /**
     * POST  /treatments : Create a new treatment for given patient.
     *
     * @param treatment the treatment to create for given patient.
     * @param patientId Patient Id whose treatment to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new treatment, or with status 400 (Bad Request) if the treatment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients/{patientId}/treatments")
    @Timed
    public ResponseEntity<Treatment> createTreatmentForPatient(@RequestBody Treatment treatment,
                                                     @PathVariable Long patientId ) throws URISyntaxException {

        log.debug("REST request to save Treatment : {}", treatment);

        setDoctor(treatment);

        treatment.setStartDate(Instant.now());
        treatment.setLastModifiedDate(Instant.now());

        treatment.setPatient(patientRepository.getOne(patientId));

        if (treatment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new treatment cannot already have an ID")).body(null);
        }
        Treatment result = treatmentRepository.save(treatment);
        return ResponseEntity.created(new URI("/api/patients/"+patientId+"/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments : Updates an existing treatment for given patient.
     *
     * @param treatment the treatment to update
     * @param patientId Patient Id whose treatment to be created.
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatment,
     * or with status 400 (Bad Request) if the treatment is not valid,
     * or with status 500 (Internal Server Error) if the treatment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients/{patientId}/treatments")
    @Timed
    public ResponseEntity<Treatment> updateTreatmentForPatient(@RequestBody Treatment treatment,
                                                     @PathVariable Long patientId ) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatment);
        if (treatment.getId() == null) {
            return createTreatmentForPatient(treatment, patientId);
        }

        setDoctor(treatment);

        treatment.setLastModifiedDate(Instant.now());

        treatment.setPatient(patientRepository.getOne(patientId));

        Treatment result = treatmentRepository.save(treatment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments : get all the treatments of logged in doctor.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of treatments in body
     */
    @GetMapping("/treatments")
    @Timed
    public List<Treatment> getAllTreatmentsOfLoggedInDoctor() {
        log.debug("REST request to get all Treatments");
        return treatmentRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  /patients/:id/treatments : get all treatments of the "id" patient.
     *
     * @param patientId the id of the patient whose treatments to be retrieved
     * @return the ResponseEntity with status 200 (OK) and with body the treatments, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{patientId}/treatments")
    @Timed
    public Set<Treatment> getPatientTreatments(@PathVariable Long patientId) {
        log.debug("REST request to get Patient : {} Treatments.", patientId);

        Set<Treatment> treatments = treatmentRepository.findByPatientId(patientId);
        for (Treatment treatment: treatments) {
            treatment.setOralExaminations(oralExaminationRepository.findByTreatmentId(treatment.getId()));
            treatment.setPayments(paymentRepository.findByTreatmentId(treatment.getId()));
            treatment.setNotes(notesRepository.findByTreatmentId(treatment.getId()));
            treatment.setPreTreatmentImages(preTreatmentImageRepository.findByTreatmentId(treatment.getId()));
            treatment.setPostTreatmentImages(postTreatmentImageRepository.findByTreatmentId(treatment.getId()));
        }

        return treatments;
    }

    /**
     * GET  /treatments/:id : get the "id" treatment.
     *
     * @param id the id of the treatment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatment, or with status 404 (Not Found)
     */
    @GetMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Treatment treatment = treatmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(treatment));
    }

    /**
     * DELETE  /treatments/:id : delete the "id" treatment.
     *
     * @param id the id of the treatment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
