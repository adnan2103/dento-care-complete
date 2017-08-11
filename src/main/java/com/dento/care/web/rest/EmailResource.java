package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Email;

import com.dento.care.repository.EmailRepository;
import com.dento.care.repository.PatientRepository;
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
 * REST controller for managing Email of Patient.
 */
@RestController
@RequestMapping("/api")
public class EmailResource {

    private final Logger log = LoggerFactory.getLogger(EmailResource.class);

    private static final String ENTITY_NAME = "email";

    private final EmailRepository emailRepository;

    @Autowired
    private PatientRepository patientRepository;

    public EmailResource(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * POST  /emails : Create a new email for given patient.
     *
     * @param email the email to create
     * @param patientId Patient Id whose email is to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new email, or with status 400 (Bad Request) if the email has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients/{patientId}/emails")
    @Timed
    public ResponseEntity<Email> createEmailForPatient(@RequestBody Email email,
                                                       @PathVariable Long patientId) throws URISyntaxException {
        log.debug("REST request to save Email : {}", email);
        if (email.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new email cannot already have an ID")).body(null);
        }
        email.setPatient(patientRepository.getOne(patientId));
        Email result = emailRepository.save(email);
        return ResponseEntity.created(new URI("/api/patients/"+patientId+"/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emails : Updates an existing email for given patient.
     *
     * @param email the email to update
     * @param patientId Patient Id whose email is to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated email,
     * or with status 400 (Bad Request) if the email is not valid,
     * or with status 500 (Internal Server Error) if the email couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients/{patientId}/emails")
    @Timed
    public ResponseEntity<Email> updateEmailForPatient(@RequestBody Email email,
                                             @PathVariable Long patientId) throws URISyntaxException {
        log.debug("REST request to update Email : {}", email);
        if (email.getId() == null) {
            return createEmailForPatient(email, patientId);
        }
        email.setPatient(patientRepository.getOne(patientId));
        Email result = emailRepository.save(email);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, email.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emails : get all the emails of Patient.
     *
     * @param patientId Patient Id whose email to be fetched.
     * @return the ResponseEntity with status 200 (OK) and the list of emails in body
     */
    @GetMapping("/patients/{patientId}/emails")
    @Timed
    public Set<Email> getPatientAllEmails(@PathVariable Long patientId) {
        log.debug("REST request to get all Emails");

        return emailRepository.findByPatientId(patientId);
    }

    /**
     * GET  /emails/:id : get the "id" email.
     *
     * @param id the id of the email to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the email, or with status 404 (Not Found)
     */
    @GetMapping("/emails/{id}")
    @Timed
    public ResponseEntity<Email> getEmail(@PathVariable Long id) {
        log.debug("REST request to get Email : {}", id);
        Email email = emailRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(email));
    }

    /**
     * DELETE  /emails/:id : delete the "id" email.
     *
     * @param id the id of the email to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emails/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        log.debug("REST request to delete Email : {}", id);
        emailRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
