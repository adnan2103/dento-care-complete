package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.PostTreatmentImage;

import com.dento.care.repository.PostTreatmentImageRepository;
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
 * REST controller for managing PostTreatmentImage.
 */
@RestController
@RequestMapping("/api")
public class PostTreatmentImageResource {

    private final Logger log = LoggerFactory.getLogger(PostTreatmentImageResource.class);

    private static final String ENTITY_NAME = "postTreatmentImage";

    private final PostTreatmentImageRepository postTreatmentImageRepository;

    public PostTreatmentImageResource(PostTreatmentImageRepository postTreatmentImageRepository) {
        this.postTreatmentImageRepository = postTreatmentImageRepository;
    }

    /**
     * POST  /post-treatment-images : Create a new postTreatmentImage.
     *
     * @param postTreatmentImage the postTreatmentImage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postTreatmentImage, or with status 400 (Bad Request) if the postTreatmentImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/post-treatment-images")
    @Timed
    public ResponseEntity<PostTreatmentImage> createPostTreatmentImage(@RequestBody PostTreatmentImage postTreatmentImage) throws URISyntaxException {
        log.debug("REST request to save PostTreatmentImage : {}", postTreatmentImage);
        if (postTreatmentImage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new postTreatmentImage cannot already have an ID")).body(null);
        }
        PostTreatmentImage result = postTreatmentImageRepository.save(postTreatmentImage);
        return ResponseEntity.created(new URI("/api/post-treatment-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /post-treatment-images : Updates an existing postTreatmentImage.
     *
     * @param postTreatmentImage the postTreatmentImage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postTreatmentImage,
     * or with status 400 (Bad Request) if the postTreatmentImage is not valid,
     * or with status 500 (Internal Server Error) if the postTreatmentImage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/post-treatment-images")
    @Timed
    public ResponseEntity<PostTreatmentImage> updatePostTreatmentImage(@RequestBody PostTreatmentImage postTreatmentImage) throws URISyntaxException {
        log.debug("REST request to update PostTreatmentImage : {}", postTreatmentImage);
        if (postTreatmentImage.getId() == null) {
            return createPostTreatmentImage(postTreatmentImage);
        }
        PostTreatmentImage result = postTreatmentImageRepository.save(postTreatmentImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postTreatmentImage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /post-treatment-images : get all the postTreatmentImages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of postTreatmentImages in body
     */
    @GetMapping("/post-treatment-images")
    @Timed
    public List<PostTreatmentImage> getAllPostTreatmentImages() {
        log.debug("REST request to get all PostTreatmentImages");
        return postTreatmentImageRepository.findAll();
    }

    /**
     * GET  /post-treatment-images/:id : get the "id" postTreatmentImage.
     *
     * @param id the id of the postTreatmentImage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postTreatmentImage, or with status 404 (Not Found)
     */
    @GetMapping("/post-treatment-images/{id}")
    @Timed
    public ResponseEntity<PostTreatmentImage> getPostTreatmentImage(@PathVariable Long id) {
        log.debug("REST request to get PostTreatmentImage : {}", id);
        PostTreatmentImage postTreatmentImage = postTreatmentImageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(postTreatmentImage));
    }

    /**
     * DELETE  /post-treatment-images/:id : delete the "id" postTreatmentImage.
     *
     * @param id the id of the postTreatmentImage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/post-treatment-images/{id}")
    @Timed
    public ResponseEntity<Void> deletePostTreatmentImage(@PathVariable Long id) {
        log.debug("REST request to delete PostTreatmentImage : {}", id);
        postTreatmentImageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
