package com.dento.care.web.rest;

import com.dento.care.DentoCareApp;

import com.dento.care.domain.OralExamination;
import com.dento.care.repository.OralExaminationRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OralExaminationResource REST controller.
 *
 * @see OralExaminationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DentoCareApp.class)
public class OralExaminationResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_COST = 1L;
    private static final Long UPDATED_COST = 2L;

    @Autowired
    private OralExaminationRepository oralExaminationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOralExaminationMockMvc;

    private OralExamination oralExamination;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OralExaminationResource oralExaminationResource = new OralExaminationResource(oralExaminationRepository);
        this.restOralExaminationMockMvc = MockMvcBuilders.standaloneSetup(oralExaminationResource)
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
    public static OralExamination createEntity(EntityManager em) {
        OralExamination oralExamination = new OralExamination()
            .description(DEFAULT_DESCRIPTION)
            .cost(DEFAULT_COST);
        return oralExamination;
    }

    @Before
    public void initTest() {
        oralExamination = createEntity(em);
    }

    @Test
    @Transactional
    public void createOralExamination() throws Exception {
        int databaseSizeBeforeCreate = oralExaminationRepository.findAll().size();

        // Create the OralExamination
        restOralExaminationMockMvc.perform(post("/api/oral-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oralExamination)))
            .andExpect(status().isCreated());

        // Validate the OralExamination in the database
        List<OralExamination> oralExaminationList = oralExaminationRepository.findAll();
        assertThat(oralExaminationList).hasSize(databaseSizeBeforeCreate + 1);
        OralExamination testOralExamination = oralExaminationList.get(oralExaminationList.size() - 1);
        assertThat(testOralExamination.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOralExamination.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createOralExaminationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oralExaminationRepository.findAll().size();

        // Create the OralExamination with an existing ID
        oralExamination.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOralExaminationMockMvc.perform(post("/api/oral-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oralExamination)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OralExamination> oralExaminationList = oralExaminationRepository.findAll();
        assertThat(oralExaminationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOralExaminations() throws Exception {
        // Initialize the database
        oralExaminationRepository.saveAndFlush(oralExamination);

        // Get all the oralExaminationList
        restOralExaminationMockMvc.perform(get("/api/oral-examinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oralExamination.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())));
    }

    @Test
    @Transactional
    public void getOralExamination() throws Exception {
        // Initialize the database
        oralExaminationRepository.saveAndFlush(oralExamination);

        // Get the oralExamination
        restOralExaminationMockMvc.perform(get("/api/oral-examinations/{id}", oralExamination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oralExamination.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOralExamination() throws Exception {
        // Get the oralExamination
        restOralExaminationMockMvc.perform(get("/api/oral-examinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOralExamination() throws Exception {
        // Initialize the database
        oralExaminationRepository.saveAndFlush(oralExamination);
        int databaseSizeBeforeUpdate = oralExaminationRepository.findAll().size();

        // Update the oralExamination
        OralExamination updatedOralExamination = oralExaminationRepository.findOne(oralExamination.getId());
        updatedOralExamination
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST);

        restOralExaminationMockMvc.perform(put("/api/oral-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOralExamination)))
            .andExpect(status().isOk());

        // Validate the OralExamination in the database
        List<OralExamination> oralExaminationList = oralExaminationRepository.findAll();
        assertThat(oralExaminationList).hasSize(databaseSizeBeforeUpdate);
        OralExamination testOralExamination = oralExaminationList.get(oralExaminationList.size() - 1);
        assertThat(testOralExamination.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOralExamination.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingOralExamination() throws Exception {
        int databaseSizeBeforeUpdate = oralExaminationRepository.findAll().size();

        // Create the OralExamination

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOralExaminationMockMvc.perform(put("/api/oral-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oralExamination)))
            .andExpect(status().isCreated());

        // Validate the OralExamination in the database
        List<OralExamination> oralExaminationList = oralExaminationRepository.findAll();
        assertThat(oralExaminationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOralExamination() throws Exception {
        // Initialize the database
        oralExaminationRepository.saveAndFlush(oralExamination);
        int databaseSizeBeforeDelete = oralExaminationRepository.findAll().size();

        // Get the oralExamination
        restOralExaminationMockMvc.perform(delete("/api/oral-examinations/{id}", oralExamination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OralExamination> oralExaminationList = oralExaminationRepository.findAll();
        assertThat(oralExaminationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OralExamination.class);
        OralExamination oralExamination1 = new OralExamination();
        oralExamination1.setId(1L);
        OralExamination oralExamination2 = new OralExamination();
        oralExamination2.setId(oralExamination1.getId());
        assertThat(oralExamination1).isEqualTo(oralExamination2);
        oralExamination2.setId(2L);
        assertThat(oralExamination1).isNotEqualTo(oralExamination2);
        oralExamination1.setId(null);
        assertThat(oralExamination1).isNotEqualTo(oralExamination2);
    }
}
