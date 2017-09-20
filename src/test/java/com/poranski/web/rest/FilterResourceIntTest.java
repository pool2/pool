package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.Filter;
import com.poranski.repository.FilterRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilterResource REST controller.
 *
 * @see FilterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class FilterResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REPLACED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPLACED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilterMockMvc;

    private Filter filter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilterResource filterResource = new FilterResource(filterRepository);
        this.restFilterMockMvc = MockMvcBuilders.standaloneSetup(filterResource)
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
    public static Filter createEntity(EntityManager em) {
        Filter filter = new Filter()
            .type(DEFAULT_TYPE)
            .size(DEFAULT_SIZE)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .replacedDate(DEFAULT_REPLACED_DATE);
        return filter;
    }

    @Before
    public void initTest() {
        filter = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilter() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isCreated());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate + 1);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFilter.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testFilter.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testFilter.getReplacedDate()).isEqualTo(DEFAULT_REPLACED_DATE);
    }

    @Test
    @Transactional
    public void createFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter with an existing ID
        filter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isBadRequest());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilters() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList
        restFilterMockMvc.perform(get("/api/filters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filter.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].replacedDate").value(hasItem(DEFAULT_REPLACED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", filter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filter.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER.toString()))
            .andExpect(jsonPath("$.replacedDate").value(DEFAULT_REPLACED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilter() throws Exception {
        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);
        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Update the filter
        Filter updatedFilter = filterRepository.findOne(filter.getId());
        updatedFilter
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .replacedDate(UPDATED_REPLACED_DATE);

        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilter)))
            .andExpect(status().isOk());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFilter.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testFilter.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testFilter.getReplacedDate()).isEqualTo(UPDATED_REPLACED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilter() throws Exception {
        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Create the Filter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isCreated());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);
        int databaseSizeBeforeDelete = filterRepository.findAll().size();

        // Get the filter
        restFilterMockMvc.perform(delete("/api/filters/{id}", filter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filter.class);
        Filter filter1 = new Filter();
        filter1.setId(1L);
        Filter filter2 = new Filter();
        filter2.setId(filter1.getId());
        assertThat(filter1).isEqualTo(filter2);
        filter2.setId(2L);
        assertThat(filter1).isNotEqualTo(filter2);
        filter1.setId(null);
        assertThat(filter1).isNotEqualTo(filter2);
    }
}
