package com.dento.care.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dento.care.domain.Address;

import com.dento.care.repository.AddressRepository;
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
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    private final AddressRepository addressRepository;

    @Autowired
    private PatientRepository patientRepository;

    public AddressResource(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * POST  /addresses : Create a new address for a patient.
     *
     * @param address the address to create.
     * @param patientId Patient Id whose address to be created.
     * @return the ResponseEntity with status 201 (Created) and with body the new address, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients/{patientId}/addresses")
    @Timed
    public ResponseEntity<Address> createAddressForPatient(@Valid @RequestBody Address address,
                                                 @PathVariable Long patientId ) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new address cannot already have an ID")).body(null);
        }

        address.setPatient(patientRepository.getOne(patientId));

        Address result = addressRepository.save(address);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing address for a given Patient.
     *
     * @param address the address to update.
     * @param patientId Patient Id whose address to be updated.
     * @return the ResponseEntity with status 200 (OK) and with body the updated address,
     * or with status 400 (Bad Request) if the address is not valid,
     * or with status 500 (Internal Server Error) if the address couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients/{patientId}/addresses")
    @Timed
    public ResponseEntity<Address> updateAddressForPatient(@Valid @RequestBody Address address,
                                                           @PathVariable Long patientId ) throws URISyntaxException {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            return createAddressForPatient(address, patientId);
        }

        address.setPatient(patientRepository.getOne(patientId));

        Address result = addressRepository.save(address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses of a given Patient.
     * @param patientId Patient Id whose address to be fetched.
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/patients/{patientId}/addresses")
    @Timed
    public Set<Address> getPatientAllAddresses(@PathVariable Long patientId) {
        log.debug("REST request to get all Addresses");
        return addressRepository.findByPatientId(patientId);
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the address, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(address));
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
