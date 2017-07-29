package com.dento.care.web.rest;

import com.dento.care.DentoCareApp;

import com.dento.care.domain.PreTreatmentImage;
import com.dento.care.repository.PreTreatmentImageRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PreTreatmentImageResource REST controller.
 *
 * @see PreTreatmentImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DentoCareApp.class)
public class PreTreatmentImageResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PreTreatmentImageRepository preTreatmentImageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreTreatmentImageMockMvc;

    private PreTreatmentImage preTreatmentImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreTreatmentImageResource preTreatmentImageResource = new PreTreatmentImageResource(preTreatmentImageRepository);
        this.restPreTreatmentImageMockMvc = MockMvcBuilders.standaloneSetup(preTreatmentImageResource)
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
    public static PreTreatmentImage createEntity(EntityManager em) {
        PreTreatmentImage preTreatmentImage = new PreTreatmentImage()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return preTreatmentImage;
    }

    @Before
    public void initTest() {
        preTreatmentImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreTreatmentImage() throws Exception {
        int databaseSizeBeforeCreate = preTreatmentImageRepository.findAll().size();

        // Create the PreTreatmentImage
        restPreTreatmentImageMockMvc.perform(post("/api/pre-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preTreatmentImage)))
            .andExpect(status().isCreated());

        // Validate the PreTreatmentImage in the database
        List<PreTreatmentImage> preTreatmentImageList = preTreatmentImageRepository.findAll();
        assertThat(preTreatmentImageList).hasSize(databaseSizeBeforeCreate + 1);
        PreTreatmentImage testPreTreatmentImage = preTreatmentImageList.get(preTreatmentImageList.size() - 1);
        assertThat(testPreTreatmentImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPreTreatmentImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPreTreatmentImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preTreatmentImageRepository.findAll().size();

        // Create the PreTreatmentImage with an existing ID
        preTreatmentImage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreTreatmentImageMockMvc.perform(post("/api/pre-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preTreatmentImage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PreTreatmentImage> preTreatmentImageList = preTreatmentImageRepository.findAll();
        assertThat(preTreatmentImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreTreatmentImages() throws Exception {
        // Initialize the database
        preTreatmentImageRepository.saveAndFlush(preTreatmentImage);

        // Get all the preTreatmentImageList
        restPreTreatmentImageMockMvc.perform(get("/api/pre-treatment-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preTreatmentImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getPreTreatmentImage() throws Exception {
        // Initialize the database
        preTreatmentImageRepository.saveAndFlush(preTreatmentImage);

        // Get the preTreatmentImage
        restPreTreatmentImageMockMvc.perform(get("/api/pre-treatment-images/{id}", preTreatmentImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preTreatmentImage.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingPreTreatmentImage() throws Exception {
        // Get the preTreatmentImage
        restPreTreatmentImageMockMvc.perform(get("/api/pre-treatment-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreTreatmentImage() throws Exception {
        // Initialize the database
        preTreatmentImageRepository.saveAndFlush(preTreatmentImage);
        int databaseSizeBeforeUpdate = preTreatmentImageRepository.findAll().size();

        // Update the preTreatmentImage
        PreTreatmentImage updatedPreTreatmentImage = preTreatmentImageRepository.findOne(preTreatmentImage.getId());
        updatedPreTreatmentImage
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPreTreatmentImageMockMvc.perform(put("/api/pre-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreTreatmentImage)))
            .andExpect(status().isOk());

        // Validate the PreTreatmentImage in the database
        List<PreTreatmentImage> preTreatmentImageList = preTreatmentImageRepository.findAll();
        assertThat(preTreatmentImageList).hasSize(databaseSizeBeforeUpdate);
        PreTreatmentImage testPreTreatmentImage = preTreatmentImageList.get(preTreatmentImageList.size() - 1);
        assertThat(testPreTreatmentImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPreTreatmentImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPreTreatmentImage() throws Exception {
        int databaseSizeBeforeUpdate = preTreatmentImageRepository.findAll().size();

        // Create the PreTreatmentImage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreTreatmentImageMockMvc.perform(put("/api/pre-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preTreatmentImage)))
            .andExpect(status().isCreated());

        // Validate the PreTreatmentImage in the database
        List<PreTreatmentImage> preTreatmentImageList = preTreatmentImageRepository.findAll();
        assertThat(preTreatmentImageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreTreatmentImage() throws Exception {
        // Initialize the database
        preTreatmentImageRepository.saveAndFlush(preTreatmentImage);
        int databaseSizeBeforeDelete = preTreatmentImageRepository.findAll().size();

        // Get the preTreatmentImage
        restPreTreatmentImageMockMvc.perform(delete("/api/pre-treatment-images/{id}", preTreatmentImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PreTreatmentImage> preTreatmentImageList = preTreatmentImageRepository.findAll();
        assertThat(preTreatmentImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreTreatmentImage.class);
        PreTreatmentImage preTreatmentImage1 = new PreTreatmentImage();
        preTreatmentImage1.setId(1L);
        PreTreatmentImage preTreatmentImage2 = new PreTreatmentImage();
        preTreatmentImage2.setId(preTreatmentImage1.getId());
        assertThat(preTreatmentImage1).isEqualTo(preTreatmentImage2);
        preTreatmentImage2.setId(2L);
        assertThat(preTreatmentImage1).isNotEqualTo(preTreatmentImage2);
        preTreatmentImage1.setId(null);
        assertThat(preTreatmentImage1).isNotEqualTo(preTreatmentImage2);
    }
}
