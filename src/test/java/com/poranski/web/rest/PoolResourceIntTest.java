package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.Pool;
import com.poranski.repository.PoolRepository;
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
 * Test class for the PoolResource REST controller.
 *
 * @see PoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class PoolResourceIntTest {

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoolMockMvc;

    private Pool pool;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoolResource poolResource = new PoolResource(poolRepository);
        this.restPoolMockMvc = MockMvcBuilders.standaloneSetup(poolResource)
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
    public static Pool createEntity(EntityManager em) {
        Pool pool = new Pool()
            .size(DEFAULT_SIZE);
        return pool;
    }

    @Before
    public void initTest() {
        pool = createEntity(em);
    }

    @Test
    @Transactional
    public void createPool() throws Exception {
        int databaseSizeBeforeCreate = poolRepository.findAll().size();

        // Create the Pool
        restPoolMockMvc.perform(post("/api/pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pool)))
            .andExpect(status().isCreated());

        // Validate the Pool in the database
        List<Pool> poolList = poolRepository.findAll();
        assertThat(poolList).hasSize(databaseSizeBeforeCreate + 1);
        Pool testPool = poolList.get(poolList.size() - 1);
        assertThat(testPool.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createPoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poolRepository.findAll().size();

        // Create the Pool with an existing ID
        pool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoolMockMvc.perform(post("/api/pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pool)))
            .andExpect(status().isBadRequest());

        // Validate the Pool in the database
        List<Pool> poolList = poolRepository.findAll();
        assertThat(poolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPools() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Get all the poolList
        restPoolMockMvc.perform(get("/api/pools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pool.getId().intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }

    @Test
    @Transactional
    public void getPool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);

        // Get the pool
        restPoolMockMvc.perform(get("/api/pools/{id}", pool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pool.getId().intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingPool() throws Exception {
        // Get the pool
        restPoolMockMvc.perform(get("/api/pools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);
        int databaseSizeBeforeUpdate = poolRepository.findAll().size();

        // Update the pool
        Pool updatedPool = poolRepository.findOne(pool.getId());
        updatedPool
            .size(UPDATED_SIZE);

        restPoolMockMvc.perform(put("/api/pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPool)))
            .andExpect(status().isOk());

        // Validate the Pool in the database
        List<Pool> poolList = poolRepository.findAll();
        assertThat(poolList).hasSize(databaseSizeBeforeUpdate);
        Pool testPool = poolList.get(poolList.size() - 1);
        assertThat(testPool.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingPool() throws Exception {
        int databaseSizeBeforeUpdate = poolRepository.findAll().size();

        // Create the Pool

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoolMockMvc.perform(put("/api/pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pool)))
            .andExpect(status().isCreated());

        // Validate the Pool in the database
        List<Pool> poolList = poolRepository.findAll();
        assertThat(poolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePool() throws Exception {
        // Initialize the database
        poolRepository.saveAndFlush(pool);
        int databaseSizeBeforeDelete = poolRepository.findAll().size();

        // Get the pool
        restPoolMockMvc.perform(delete("/api/pools/{id}", pool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pool> poolList = poolRepository.findAll();
        assertThat(poolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pool.class);
        Pool pool1 = new Pool();
        pool1.setId(1L);
        Pool pool2 = new Pool();
        pool2.setId(pool1.getId());
        assertThat(pool1).isEqualTo(pool2);
        pool2.setId(2L);
        assertThat(pool1).isNotEqualTo(pool2);
        pool1.setId(null);
        assertThat(pool1).isNotEqualTo(pool2);
    }
}
