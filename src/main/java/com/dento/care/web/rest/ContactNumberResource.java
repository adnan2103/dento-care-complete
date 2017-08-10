package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.ContactNumber;

import com.dento.care.repository.ContactNumberRepository;
import com.dento.care.repository.PatientRepository;
import com.dento.care.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing ContactNumber of Patient.
 */
@RestController
@RequestMapping("/api")
public class ContactNumberResource {

    private final Logger log = LoggerFactory.getLogger(ContactNumberResource.class);

    private static final String ENTITY_NAME = "contactNumber";

    private final ContactNumberRepository contactNumberRepository;

    @Autowired
    private PatientRepository patientRepository;

    public ContactNumberResource(ContactNumberRepository contactNumberRepository) {
        this.contactNumberRepository = contactNumberRepository;
    }

    /**
     * POST  /contact-numbers : Create a new contactNumber of a patient.
     *
     * @param contactNumber the contactNumber to create
     * @param patientId Patient Id whose contact is to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new contactNumber, or with status 400 (Bad Request) if the contactNumber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients/{patientId}/contact-numbers")
    @Timed
    public ResponseEntity<ContactNumber> createContactNumberForPatient(@Valid @RequestBody ContactNumber contactNumber,
                                                                       @PathVariable Long patientId ) throws URISyntaxException {
        log.debug("REST request to save ContactNumber : {}", contactNumber);
        if (contactNumber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contactNumber cannot already have an ID")).body(null);
        }

        contactNumber.setPatient(patientRepository.getOne(patientId));

        ContactNumber result = contactNumberRepository.save(contactNumber);
        return ResponseEntity.created(new URI("/api/contact-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-numbers : Updates an existing contactNumber of a patient.
     *
     * @param contactNumber the contactNumber to update.
     * @param patientId Patient Id whose contacts to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactNumber,
     * or with status 400 (Bad Request) if the contactNumber is not valid,
     * or with status 500 (Internal Server Error) if the contactNumber couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients/{patientId}/contact-numbers")
    @Timed
    public ResponseEntity<ContactNumber> updateContactNumberForPatient(@Valid @RequestBody ContactNumber contactNumber,
                                                             @PathVariable Long patientId) throws URISyntaxException {
        log.debug("REST request to update ContactNumber : {}", contactNumber);
        if (contactNumber.getId() == null) {
            return createContactNumberForPatient(contactNumber, patientId);
        }

        contactNumber.setPatient(patientRepository.getOne(patientId));

        ContactNumber result = contactNumberRepository.save(contactNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactNumber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-numbers : get all the contactNumbers of a patient.
     *
     * @parma patientId Patient Id whose contacts to be retrieved.
     * @return the ResponseEntity with status 200 (OK) and the list of contactNumbers in body
     */
    @GetMapping("/patients/{patientId}/contact-numbers")
    @Timed
    public Set<ContactNumber> getPatientAllContactNumbers(@PathVariable Long patientId) {
        log.debug("REST request to get all ContactNumbers");
        return contactNumberRepository.findByPatientId(patientId);
    }

    /**
     * GET  /contact-numbers/:id : get the "id" contactNumber.
     *
     * @param id the id of the contactNumber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactNumber, or with status 404 (Not Found)
     */
    @GetMapping("/contact-numbers/{id}")
    @Timed
    public ResponseEntity<ContactNumber> getContactNumber(@PathVariable Long id) {
        log.debug("REST request to get ContactNumber : {}", id);
        ContactNumber contactNumber = contactNumberRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactNumber));
    }

    /**
     * DELETE  /contact-numbers/:id : delete the "id" contactNumber.
     *
     * @param id the id of the contactNumber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-numbers/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactNumber(@PathVariable Long id) {
        log.debug("REST request to delete ContactNumber : {}", id);
        contactNumberRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
