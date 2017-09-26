package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.InventoryUsed;
import com.poranski.repository.InventoryUsedRepository;
import com.poranski.web.rest.errors.ExceptionTranslator;

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
 * Test class for the InventoryUsedResource REST controller.
 *
 * @see InventoryUsedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class InventoryUsedResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private InventoryUsedRepository inventoryUsedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventoryUsedMockMvc;

    private InventoryUsed inventoryUsed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryUsedResource inventoryUsedResource = new InventoryUsedResource(inventoryUsedRepository);
        this.restInventoryUsedMockMvc = MockMvcBuilders.standaloneSetup(inventoryUsedResource)
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
    public static InventoryUsed createEntity(EntityManager em) {
        InventoryUsed inventoryUsed = new InventoryUsed()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .note(DEFAULT_NOTE);
        return inventoryUsed;
    }

    @Before
    public void initTest() {
        inventoryUsed = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryUsed() throws Exception {
        int databaseSizeBeforeCreate = inventoryUsedRepository.findAll().size();

        // Create the InventoryUsed
        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsed)))
            .andExpect(status().isCreated());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryUsed testInventoryUsed = inventoryUsedList.get(inventoryUsedList.size() - 1);
        assertThat(testInventoryUsed.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryUsed.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInventoryUsed.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createInventoryUsedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryUsedRepository.findAll().size();

        // Create the InventoryUsed with an existing ID
        inventoryUsed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryUsedMockMvc.perform(post("/api/inventory-useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsed)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInventoryUseds() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get all the inventoryUsedList
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryUsed.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);

        // Get the inventoryUsed
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/{id}", inventoryUsed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryUsed.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryUsed() throws Exception {
        // Get the inventoryUsed
        restInventoryUsedMockMvc.perform(get("/api/inventory-useds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);
        int databaseSizeBeforeUpdate = inventoryUsedRepository.findAll().size();

        // Update the inventoryUsed
        InventoryUsed updatedInventoryUsed = inventoryUsedRepository.findOne(inventoryUsed.getId());
        updatedInventoryUsed
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .note(UPDATED_NOTE);

        restInventoryUsedMockMvc.perform(put("/api/inventory-useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryUsed)))
            .andExpect(status().isOk());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeUpdate);
        InventoryUsed testInventoryUsed = inventoryUsedList.get(inventoryUsedList.size() - 1);
        assertThat(testInventoryUsed.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryUsed.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInventoryUsed.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryUsed() throws Exception {
        int databaseSizeBeforeUpdate = inventoryUsedRepository.findAll().size();

        // Create the InventoryUsed

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventoryUsedMockMvc.perform(put("/api/inventory-useds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryUsed)))
            .andExpect(status().isCreated());

        // Validate the InventoryUsed in the database
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventoryUsed() throws Exception {
        // Initialize the database
        inventoryUsedRepository.saveAndFlush(inventoryUsed);
        int databaseSizeBeforeDelete = inventoryUsedRepository.findAll().size();

        // Get the inventoryUsed
        restInventoryUsedMockMvc.perform(delete("/api/inventory-useds/{id}", inventoryUsed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryUsed> inventoryUsedList = inventoryUsedRepository.findAll();
        assertThat(inventoryUsedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryUsed.class);
        InventoryUsed inventoryUsed1 = new InventoryUsed();
        inventoryUsed1.setId(1L);
        InventoryUsed inventoryUsed2 = new InventoryUsed();
        inventoryUsed2.setId(inventoryUsed1.getId());
        assertThat(inventoryUsed1).isEqualTo(inventoryUsed2);
        inventoryUsed2.setId(2L);
        assertThat(inventoryUsed1).isNotEqualTo(inventoryUsed2);
        inventoryUsed1.setId(null);
        assertThat(inventoryUsed1).isNotEqualTo(inventoryUsed2);
    }
}
