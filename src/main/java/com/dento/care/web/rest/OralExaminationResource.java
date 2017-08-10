package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.OralExamination;

import com.dento.care.repository.OralExaminationRepository;
import com.dento.care.repository.TreatmentRepository;
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
 * REST controller for managing OralExamination.
 */
@RestController
@RequestMapping("/api")
public class OralExaminationResource {

    private final Logger log = LoggerFactory.getLogger(OralExaminationResource.class);

    private static final String ENTITY_NAME = "oralExamination";

    private final OralExaminationRepository oralExaminationRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    public OralExaminationResource(OralExaminationRepository oralExaminationRepository) {
        this.oralExaminationRepository = oralExaminationRepository;
    }

    /**
     * POST  /oral-examinations : Create a new oralExamination for a given treatment.
     *
     * @param oralExamination the oralExamination to create
     * @param treatmentId Treatment Id whose oral examiniation is need to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new oralExamination, or with status 400 (Bad Request) if the oralExamination has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatments/{treatmentId}/oral-examinations")
    @Timed
    public ResponseEntity<OralExamination> createOralExaminationForTreatment(@RequestBody OralExamination oralExamination,
                                                                             @PathVariable Long treatmentId ) throws URISyntaxException {
        log.debug("REST request to save OralExamination : {}", oralExamination);
        if (oralExamination.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new oralExamination cannot already have an ID")).body(null);
        }
        oralExamination.setTreatment(treatmentRepository.getOne(treatmentId));

        OralExamination result = oralExaminationRepository.save(oralExamination);
        return ResponseEntity.created(new URI("/api/oral-examinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oral-examinations : Updates an existing oralExamination for a given treatment.
     *
     * @param oralExamination the oralExamination to update
     * @param treatmentId Treatment Id whose oral examiniation is need to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated oralExamination,
     * or with status 400 (Bad Request) if the oralExamination is not valid,
     * or with status 500 (Internal Server Error) if the oralExamination couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatments/{treatmentId}/oral-examinations")
    @Timed
    public ResponseEntity<OralExamination> updateOralExaminationForTreatment(@RequestBody OralExamination oralExamination,
                                                                 @PathVariable Long treatmentId ) throws URISyntaxException {
        log.debug("REST request to update OralExamination : {}", oralExamination);
        if (oralExamination.getId() == null) {
            return createOralExaminationForTreatment(oralExamination, treatmentId);
        }
        oralExamination.setTreatment(treatmentRepository.getOne(treatmentId));

        OralExamination result = oralExaminationRepository.save(oralExamination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oralExamination.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oral-examinations : get all the oralExaminations for given treatment.
     * @param treatmentId Treatment Id whose oral examinations need to be fetched.
     * @return the ResponseEntity with status 200 (OK) and the list of oralExaminations in body
     */
    @GetMapping("/treatments/{treatmentId}/oral-examinations")
    @Timed
    public Set<OralExamination> getAllOralExaminationsForTreatment(@PathVariable Long treatmentId) {
        log.debug("REST request to get all OralExaminations");
        return oralExaminationRepository.findByTreatmentId(treatmentId);
    }

    /**
     * GET  /oral-examinations/:id : get the "id" oralExamination.
     *
     * @param id the id of the oralExamination to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oralExamination, or with status 404 (Not Found)
     */
    @GetMapping("/oral-examinations/{id}")
    @Timed
    public ResponseEntity<OralExamination> getOralExamination(@PathVariable Long id) {
        log.debug("REST request to get OralExamination : {}", id);
        OralExamination oralExamination = oralExaminationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(oralExamination));
    }

    /**
     * DELETE  /oral-examinations/:id : delete the "id" oralExamination.
     *
     * @param id the id of the oralExamination to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oral-examinations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOralExamination(@PathVariable Long id) {
        log.debug("REST request to delete OralExamination : {}", id);
        oralExaminationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
