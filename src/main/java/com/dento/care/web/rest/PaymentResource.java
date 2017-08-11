package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Payment;

import com.dento.care.repository.PaymentRepository;
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
 * REST controller for managing Payment.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    private final PaymentRepository paymentRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    public PaymentResource(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * POST  /payments : Create a new payment for the given treatment.
     *
     * @param payment the payment to create.
     * @param treatmentId Treatment Id whose payment is to be created.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new payment, or with status 400 (Bad Request) if the payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatments/{treatmentId}/payments")
    @Timed
    public ResponseEntity<Payment> createPaymentForTreatment(@RequestBody Payment payment,
                                                 @PathVariable Long treatmentId ) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", payment);
        if (payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payment cannot already have an ID")).body(null);
        }

        payment.setTreatment(treatmentRepository.getOne(treatmentId));

        Payment result = paymentRepository.save(payment);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payments : Updates an existing payment for a given treatment.
     *
     * @param payment the payment to update
     * @param treatmentId Treatment Id whose payment is to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated payment,
     * or with status 400 (Bad Request) if the payment is not valid,
     * or with status 500 (Internal Server Error) if the payment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatments/{treatmentId}/payments")
    @Timed
    public ResponseEntity<Payment> updatePaymentForTreatment(@RequestBody Payment payment,
                                                             @PathVariable Long treatmentId ) throws URISyntaxException {
        log.debug("REST request to update Payment : {}", payment);
        if (payment.getId() == null) {
            return createPaymentForTreatment(payment, treatmentId);
        }

        payment.setTreatment(treatmentRepository.getOne(treatmentId));

        Payment result = paymentRepository.save(payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payments : get all the payments for a give treatment.
     * @param treatmentId Treatment whose all payments need to be fetched.
     * @return the ResponseEntity with status 200 (OK) and the list of payments in body
     */
    @GetMapping("/treatments/{treatmentId}/payments")
    @Timed
    public Set<Payment> getAllPaymentsForTreatment(@PathVariable Long treatmentId) {
        log.debug("REST request to get all Payments");
        return paymentRepository.findByTreatmentId(treatmentId);
    }

    /**
     * GET  /payments/:id : get the "id" payment.
     *
     * @param id the id of the payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payment, or with status 404 (Not Found)
     */
    @GetMapping("/treatments/{treatmentId}/payments/{id}")
    @Timed
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Payment payment = paymentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payment));
    }

    /**
     * DELETE  /payments/:id : delete the "id" payment.
     *
     * @param id the id of the payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatments/{treatmentId}/payments/{id}")
    @Timed
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
