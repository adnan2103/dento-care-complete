package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.PreTreatmentImage;

import com.dento.care.repository.PreTreatmentImageRepository;
import com.dento.care.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PreTreatmentImage.
 */
@RestController
@RequestMapping("/api")
public class PreTreatmentImageResource {

    private final Logger log = LoggerFactory.getLogger(PreTreatmentImageResource.class);

    private static final String ENTITY_NAME = "preTreatmentImage";

    private final PreTreatmentImageRepository preTreatmentImageRepository;

    public PreTreatmentImageResource(PreTreatmentImageRepository preTreatmentImageRepository) {
        this.preTreatmentImageRepository = preTreatmentImageRepository;
    }

    /**
     * POST  /pre-treatment-images : Create a new preTreatmentImage.
     *
     * @param preTreatmentImage the preTreatmentImage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preTreatmentImage, or with status 400 (Bad Request) if the preTreatmentImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pre-treatment-images")
    @Timed
    public ResponseEntity<PreTreatmentImage> createPreTreatmentImage(@RequestBody PreTreatmentImage preTreatmentImage) throws URISyntaxException {
        log.debug("REST request to save PreTreatmentImage : {}", preTreatmentImage);
        if (preTreatmentImage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new preTreatmentImage cannot already have an ID")).body(null);
        }
        PreTreatmentImage result = preTreatmentImageRepository.save(preTreatmentImage);
        return ResponseEntity.created(new URI("/api/pre-treatment-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pre-treatment-images : Updates an existing preTreatmentImage.
     *
     * @param preTreatmentImage the preTreatmentImage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preTreatmentImage,
     * or with status 400 (Bad Request) if the preTreatmentImage is not valid,
     * or with status 500 (Internal Server Error) if the preTreatmentImage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pre-treatment-images")
    @Timed
    public ResponseEntity<PreTreatmentImage> updatePreTreatmentImage(@RequestBody PreTreatmentImage preTreatmentImage) throws URISyntaxException {
        log.debug("REST request to update PreTreatmentImage : {}", preTreatmentImage);
        if (preTreatmentImage.getId() == null) {
            return createPreTreatmentImage(preTreatmentImage);
        }
        PreTreatmentImage result = preTreatmentImageRepository.save(preTreatmentImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preTreatmentImage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pre-treatment-images : get all the preTreatmentImages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preTreatmentImages in body
     */
    @GetMapping("/pre-treatment-images")
    @Timed
    public List<PreTreatmentImage> getAllPreTreatmentImages() {
        log.debug("REST request to get all PreTreatmentImages");
        return preTreatmentImageRepository.findAll();
    }

    /**
     * GET  /pre-treatment-images/:id : get the "id" preTreatmentImage.
     *
     * @param id the id of the preTreatmentImage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preTreatmentImage, or with status 404 (Not Found)
     */
    @GetMapping("/pre-treatment-images/{id}")
    @Timed
    public ResponseEntity<PreTreatmentImage> getPreTreatmentImage(@PathVariable Long id) {
        log.debug("REST request to get PreTreatmentImage : {}", id);
        PreTreatmentImage preTreatmentImage = preTreatmentImageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preTreatmentImage));
    }

    /**
     * DELETE  /pre-treatment-images/:id : delete the "id" preTreatmentImage.
     *
     * @param id the id of the preTreatmentImage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pre-treatment-images/{id}")
    @Timed
    public ResponseEntity<Void> deletePreTreatmentImage(@PathVariable Long id) {
        log.debug("REST request to delete PreTreatmentImage : {}", id);
        preTreatmentImageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
