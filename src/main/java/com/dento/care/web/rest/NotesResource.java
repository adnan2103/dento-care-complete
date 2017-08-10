package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Notes;

import com.dento.care.repository.NotesRepository;
import com.dento.care.repository.TreatmentRepository;
import com.dento.care.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Notes.
 */
@RestController
@RequestMapping("/api")
public class NotesResource {

    private final Logger log = LoggerFactory.getLogger(NotesResource.class);

    private static final String ENTITY_NAME = "notes";

    private final NotesRepository notesRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    public NotesResource(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    /**
     * POST  /notes : Create a new notes for a given treatment.
     *
     * @param notes the notes to create
     * @param treatmentId Treatment Id whose notes to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new notes, or with status 400 (Bad Request) if the notes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatments/{treatmentId}/notes")
    @Timed
    public ResponseEntity<Notes> createNotesForTreatment(@RequestBody Notes notes,
                                             @PathVariable Long treatmentId ) throws URISyntaxException {
        log.debug("REST request to save Notes : {}", notes);
        if (notes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new notes cannot already have an ID")).body(null);
        }

        notes.setTreatment(treatmentRepository.getOne(treatmentId));

        Notes result = notesRepository.save(notes);
        return ResponseEntity.created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notes : Updates an existing notes for a treatment.
     *
     * @param notes the notes to update
     * @param treatmentId Treatment Id whose note to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated notes,
     * or with status 400 (Bad Request) if the notes is not valid,
     * or with status 500 (Internal Server Error) if the notes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatments/{treatmentId}/notes")
    @Timed
    public ResponseEntity<Notes> updateNotesForTreatment(@RequestBody Notes notes,
                                             @PathVariable Long treatmentId) throws URISyntaxException {
        log.debug("REST request to update Notes : {}", notes);
        if (notes.getId() == null) {
            return createNotesForTreatment(notes, treatmentId);
        }

        notes.setTreatment(treatmentRepository.getOne(treatmentId));

        Notes result = notesRepository.save(notes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notes : get all the notes of a treatment.
     * @param treatmentId Treatment Id whose notes to be fetched.
     * @return the ResponseEntity with status 200 (OK) and the list of notes in body
     */
    @GetMapping("/treatments/{treatmentId}/notes")
    @Timed
    public Set<Notes> getAllNotesForTreatment(@PathVariable Long treatmentId) {
        log.debug("REST request to get all Notes");
        return notesRepository.findByTreatmentId(treatmentId);
    }

    /**
     * GET  /notes/:id : get the "id" notes.
     *
     * @param id the id of the notes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notes, or with status 404 (Not Found)
     */
    @GetMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Notes> getNotes(@PathVariable Long id) {
        log.debug("REST request to get Notes : {}", id);
        Notes notes = notesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notes));
    }

    /**
     * DELETE  /notes/:id : delete the "id" notes.
     *
     * @param id the id of the notes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id) {
        log.debug("REST request to delete Notes : {}", id);
        notesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
