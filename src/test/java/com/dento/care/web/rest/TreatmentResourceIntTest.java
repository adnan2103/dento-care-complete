package com.dento.care.web.rest;

import com.dento.care.DentoCareApp;

import com.dento.care.domain.Treatment;
import com.dento.care.repository.TreatmentRepository;
import com.dento.care.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.dento.care.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dento.care.domain.enumeration.Status;
/**
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DentoCareApp.class)
public class TreatmentResourceIntTest {

    private static final String DEFAULT_CHIEF_COMPLAIN_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CHIEF_COMPLAIN_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.NEW;
    private static final Status UPDATED_STATUS = Status.INPROGRESS;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TreatmentResource treatmentResource = new TreatmentResource(treatmentRepository);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .chiefComplainDescription(DEFAULT_CHIEF_COMPLAIN_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .startDate(DEFAULT_START_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return treatment;
    }

    @Before
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getChiefComplainDescription()).isEqualTo(DEFAULT_CHIEF_COMPLAIN_DESCRIPTION);
        assertThat(testTreatment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTreatment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTreatment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment with an existing ID
        treatment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].chiefComplainDescription").value(hasItem(DEFAULT_CHIEF_COMPLAIN_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.chiefComplainDescription").value(DEFAULT_CHIEF_COMPLAIN_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findOne(treatment.getId());
        updatedTreatment
            .chiefComplainDescription(UPDATED_CHIEF_COMPLAIN_DESCRIPTION)
            .status(UPDATED_STATUS)
            .startDate(UPDATED_START_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatment)))
            .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getChiefComplainDescription()).isEqualTo(UPDATED_CHIEF_COMPLAIN_DESCRIPTION);
        assertThat(testTreatment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTreatment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTreatment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatment() throws Exception {
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Create the Treatment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Treatment.class);
        Treatment treatment1 = new Treatment();
        treatment1.setId(1L);
        Treatment treatment2 = new Treatment();
        treatment2.setId(treatment1.getId());
        assertThat(treatment1).isEqualTo(treatment2);
        treatment2.setId(2L);
        assertThat(treatment1).isNotEqualTo(treatment2);
        treatment1.setId(null);
        assertThat(treatment1).isNotEqualTo(treatment2);
    }
}
