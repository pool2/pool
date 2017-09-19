package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.InventoryItem;
import com.poranski.repository.InventoryItemRepository;
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
 * Test class for the InventoryItemResource REST controller.
 *
 * @see InventoryItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class InventoryItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventoryItemMockMvc;

    private InventoryItem inventoryItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryItemResource inventoryItemResource = new InventoryItemResource(inventoryItemRepository);
        this.restInventoryItemMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemResource)
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
    public static InventoryItem createEntity(EntityManager em) {
        InventoryItem inventoryItem = new InventoryItem()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY);
        return inventoryItem;
    }

    @Before
    public void initTest() {
        inventoryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryItem() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemRepository.findAll().size();

        // Create the InventoryItem
        restInventoryItemMockMvc.perform(post("/api/inventory-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isCreated());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryItem testInventoryItem = inventoryItemList.get(inventoryItemList.size() - 1);
        assertThat(testInventoryItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createInventoryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemRepository.findAll().size();

        // Create the InventoryItem with an existing ID
        inventoryItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryItemMockMvc.perform(post("/api/inventory-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInventoryItems() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList
        restInventoryItemMockMvc.perform(get("/api/inventory-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventory-items/{id}", inventoryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItem() throws Exception {
        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventory-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        int databaseSizeBeforeUpdate = inventoryItemRepository.findAll().size();

        // Update the inventoryItem
        InventoryItem updatedInventoryItem = inventoryItemRepository.findOne(inventoryItem.getId());
        updatedInventoryItem
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY);

        restInventoryItemMockMvc.perform(put("/api/inventory-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryItem)))
            .andExpect(status().isOk());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeUpdate);
        InventoryItem testInventoryItem = inventoryItemList.get(inventoryItemList.size() - 1);
        assertThat(testInventoryItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryItem() throws Exception {
        int databaseSizeBeforeUpdate = inventoryItemRepository.findAll().size();

        // Create the InventoryItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventoryItemMockMvc.perform(put("/api/inventory-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isCreated());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        int databaseSizeBeforeDelete = inventoryItemRepository.findAll().size();

        // Get the inventoryItem
        restInventoryItemMockMvc.perform(delete("/api/inventory-items/{id}", inventoryItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryItem.class);
        InventoryItem inventoryItem1 = new InventoryItem();
        inventoryItem1.setId(1L);
        InventoryItem inventoryItem2 = new InventoryItem();
        inventoryItem2.setId(inventoryItem1.getId());
        assertThat(inventoryItem1).isEqualTo(inventoryItem2);
        inventoryItem2.setId(2L);
        assertThat(inventoryItem1).isNotEqualTo(inventoryItem2);
        inventoryItem1.setId(null);
        assertThat(inventoryItem1).isNotEqualTo(inventoryItem2);
    }
}
