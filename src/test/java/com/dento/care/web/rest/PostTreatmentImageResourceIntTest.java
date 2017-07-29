package com.dento.care.web.rest;

import com.dento.care.DentoCareApp;

import com.dento.care.domain.PostTreatmentImage;
import com.dento.care.repository.PostTreatmentImageRepository;
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
 * Test class for the PostTreatmentImageResource REST controller.
 *
 * @see PostTreatmentImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DentoCareApp.class)
public class PostTreatmentImageResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PostTreatmentImageRepository postTreatmentImageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPostTreatmentImageMockMvc;

    private PostTreatmentImage postTreatmentImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PostTreatmentImageResource postTreatmentImageResource = new PostTreatmentImageResource(postTreatmentImageRepository);
        this.restPostTreatmentImageMockMvc = MockMvcBuilders.standaloneSetup(postTreatmentImageResource)
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
    public static PostTreatmentImage createEntity(EntityManager em) {
        PostTreatmentImage postTreatmentImage = new PostTreatmentImage()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return postTreatmentImage;
    }

    @Before
    public void initTest() {
        postTreatmentImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostTreatmentImage() throws Exception {
        int databaseSizeBeforeCreate = postTreatmentImageRepository.findAll().size();

        // Create the PostTreatmentImage
        restPostTreatmentImageMockMvc.perform(post("/api/post-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postTreatmentImage)))
            .andExpect(status().isCreated());

        // Validate the PostTreatmentImage in the database
        List<PostTreatmentImage> postTreatmentImageList = postTreatmentImageRepository.findAll();
        assertThat(postTreatmentImageList).hasSize(databaseSizeBeforeCreate + 1);
        PostTreatmentImage testPostTreatmentImage = postTreatmentImageList.get(postTreatmentImageList.size() - 1);
        assertThat(testPostTreatmentImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPostTreatmentImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPostTreatmentImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postTreatmentImageRepository.findAll().size();

        // Create the PostTreatmentImage with an existing ID
        postTreatmentImage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostTreatmentImageMockMvc.perform(post("/api/post-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postTreatmentImage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PostTreatmentImage> postTreatmentImageList = postTreatmentImageRepository.findAll();
        assertThat(postTreatmentImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPostTreatmentImages() throws Exception {
        // Initialize the database
        postTreatmentImageRepository.saveAndFlush(postTreatmentImage);

        // Get all the postTreatmentImageList
        restPostTreatmentImageMockMvc.perform(get("/api/post-treatment-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postTreatmentImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getPostTreatmentImage() throws Exception {
        // Initialize the database
        postTreatmentImageRepository.saveAndFlush(postTreatmentImage);

        // Get the postTreatmentImage
        restPostTreatmentImageMockMvc.perform(get("/api/post-treatment-images/{id}", postTreatmentImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postTreatmentImage.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingPostTreatmentImage() throws Exception {
        // Get the postTreatmentImage
        restPostTreatmentImageMockMvc.perform(get("/api/post-treatment-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostTreatmentImage() throws Exception {
        // Initialize the database
        postTreatmentImageRepository.saveAndFlush(postTreatmentImage);
        int databaseSizeBeforeUpdate = postTreatmentImageRepository.findAll().size();

        // Update the postTreatmentImage
        PostTreatmentImage updatedPostTreatmentImage = postTreatmentImageRepository.findOne(postTreatmentImage.getId());
        updatedPostTreatmentImage
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPostTreatmentImageMockMvc.perform(put("/api/post-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPostTreatmentImage)))
            .andExpect(status().isOk());

        // Validate the PostTreatmentImage in the database
        List<PostTreatmentImage> postTreatmentImageList = postTreatmentImageRepository.findAll();
        assertThat(postTreatmentImageList).hasSize(databaseSizeBeforeUpdate);
        PostTreatmentImage testPostTreatmentImage = postTreatmentImageList.get(postTreatmentImageList.size() - 1);
        assertThat(testPostTreatmentImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPostTreatmentImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPostTreatmentImage() throws Exception {
        int databaseSizeBeforeUpdate = postTreatmentImageRepository.findAll().size();

        // Create the PostTreatmentImage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPostTreatmentImageMockMvc.perform(put("/api/post-treatment-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postTreatmentImage)))
            .andExpect(status().isCreated());

        // Validate the PostTreatmentImage in the database
        List<PostTreatmentImage> postTreatmentImageList = postTreatmentImageRepository.findAll();
        assertThat(postTreatmentImageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePostTreatmentImage() throws Exception {
        // Initialize the database
        postTreatmentImageRepository.saveAndFlush(postTreatmentImage);
        int databaseSizeBeforeDelete = postTreatmentImageRepository.findAll().size();

        // Get the postTreatmentImage
        restPostTreatmentImageMockMvc.perform(delete("/api/post-treatment-images/{id}", postTreatmentImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PostTreatmentImage> postTreatmentImageList = postTreatmentImageRepository.findAll();
        assertThat(postTreatmentImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostTreatmentImage.class);
        PostTreatmentImage postTreatmentImage1 = new PostTreatmentImage();
        postTreatmentImage1.setId(1L);
        PostTreatmentImage postTreatmentImage2 = new PostTreatmentImage();
        postTreatmentImage2.setId(postTreatmentImage1.getId());
        assertThat(postTreatmentImage1).isEqualTo(postTreatmentImage2);
        postTreatmentImage2.setId(2L);
        assertThat(postTreatmentImage1).isNotEqualTo(postTreatmentImage2);
        postTreatmentImage1.setId(null);
        assertThat(postTreatmentImage1).isNotEqualTo(postTreatmentImage2);
    }
}
