package com.dento.care.web.rest;

import com.dento.care.DentoCareApp;

import com.dento.care.domain.ContactNumber;
import com.dento.care.repository.ContactNumberRepository;
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
 * Test class for the ContactNumberResource REST controller.
 *
 * @see ContactNumberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DentoCareApp.class)
public class ContactNumberResourceIntTest {

    private static final Integer DEFAULT_CONTACT_NUMBER = 9999;
    private static final Integer UPDATED_CONTACT_NUMBER = 9998;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactNumberMockMvc;

    private ContactNumber contactNumber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactNumberResource contactNumberResource = new ContactNumberResource(contactNumberRepository);
        this.restContactNumberMockMvc = MockMvcBuilders.standaloneSetup(contactNumberResource)
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
    public static ContactNumber createEntity(EntityManager em) {
        ContactNumber contactNumber = new ContactNumber()
            .contactNumber(DEFAULT_CONTACT_NUMBeR);
        return contactNumber;
    }

    @Before
    public void initTest() {
        contactNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactNumber() throws Exception {
        int databaseSizeBeforeCreate = contactNumberRepository.findAll().size();

        // Create the ContactNumber
        restContactNumberMockMvc.perform(post("/api/contact-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactNumber)))
            .andExpect(status().isCreated());

        // Validate the ContactNumber in the database
        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeCreate + 1);
        ContactNumber testContactNumber = contactNumberList.get(contactNumberList.size() - 1);
        assertThat(testContactNumber.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBeR);
    }

    @Test
    @Transactional
    public void createContactNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactNumberRepository.findAll().size();

        // Create the ContactNumber with an existing ID
        contactNumber.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactNumberMockMvc.perform(post("/api/contact-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactNumber)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactNumberRepository.findAll().size();
        // set the field null
        contactNumber.setContactNumber(null);

        // Create the ContactNumber, which fails.

        restContactNumberMockMvc.perform(post("/api/contact-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactNumber)))
            .andExpect(status().isBadRequest());

        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactNumbers() throws Exception {
        // Initialize the database
        contactNumberRepository.saveAndFlush(contactNumber);

        // Get all the contactNumberList
        restContactNumberMockMvc.perform(get("/api/contact-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));
    }

    @Test
    @Transactional
    public void getContactNumber() throws Exception {
        // Initialize the database
        contactNumberRepository.saveAndFlush(contactNumber);

        // Get the contactNumber
        restContactNumberMockMvc.perform(get("/api/contact-numbers/{id}", contactNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactNumber.getId().intValue()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingContactNumber() throws Exception {
        // Get the contactNumber
        restContactNumberMockMvc.perform(get("/api/contact-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactNumber() throws Exception {
        // Initialize the database
        contactNumberRepository.saveAndFlush(contactNumber);
        int databaseSizeBeforeUpdate = contactNumberRepository.findAll().size();

        // Update the contactNumber
        ContactNumber updatedContactNumber = contactNumberRepository.findOne(contactNumber.getId());
        updatedContactNumber
            .contactNumber(UPDATED_CONTACT_NUMBER);

        restContactNumberMockMvc.perform(put("/api/contact-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactNumber)))
            .andExpect(status().isOk());

        // Validate the ContactNumber in the database
        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeUpdate);
        ContactNumber testContactNumber = contactNumberList.get(contactNumberList.size() - 1);
        assertThat(testContactNumber.getContactNumber()).isEqualTo(UPDATED_CONTACT_Number);
    }

    @Test
    @Transactional
    public void updateNonExistingContactNumber() throws Exception {
        int databaseSizeBeforeUpdate = contactNumberRepository.findAll().size();

        // Create the ContactNumber

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactNumberMockMvc.perform(put("/api/contact-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactNumber)))
            .andExpect(status().isCreated());

        // Validate the ContactNumber in the database
        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactNumber() throws Exception {
        // Initialize the database
        contactNumberRepository.saveAndFlush(contactNumber);
        int databaseSizeBeforeDelete = contactNumberRepository.findAll().size();

        // Get the contactNumber
        restContactNumberMockMvc.perform(delete("/api/contact-numbers/{id}", contactNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactNumber> contactNumberList = contactNumberRepository.findAll();
        assertThat(contactNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactNumber.class);
        ContactNumber contactNumber1 = new ContactNumber();
        contactNumber1.setId(1L);
        ContactNumber contactNumber2 = new ContactNumber();
        contactNumber2.setId(contactNumber1.getId());
        assertThat(contactNumber1).isEqualTo(contactNumber2);
        contactNumber2.setId(2L);
        assertThat(contactNumber1).isNotEqualTo(contactNumber2);
        contactNumber1.setId(null);
        assertThat(contactNumber1).isNotEqualTo(contactNumber2);
    }
}
