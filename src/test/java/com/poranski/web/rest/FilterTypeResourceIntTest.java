package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.FilterType;
import com.poranski.repository.FilterTypeRepository;
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
 * Test class for the FilterTypeResource REST controller.
 *
 * @see FilterTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class FilterTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FilterTypeRepository filterTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilterTypeMockMvc;

    private FilterType filterType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilterTypeResource filterTypeResource = new FilterTypeResource(filterTypeRepository);
        this.restFilterTypeMockMvc = MockMvcBuilders.standaloneSetup(filterTypeResource)
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
    public static FilterType createEntity(EntityManager em) {
        FilterType filterType = new FilterType()
            .name(DEFAULT_NAME);
        return filterType;
    }

    @Before
    public void initTest() {
        filterType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilterType() throws Exception {
        int databaseSizeBeforeCreate = filterTypeRepository.findAll().size();

        // Create the FilterType
        restFilterTypeMockMvc.perform(post("/api/filter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterType)))
            .andExpect(status().isCreated());

        // Validate the FilterType in the database
        List<FilterType> filterTypeList = filterTypeRepository.findAll();
        assertThat(filterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FilterType testFilterType = filterTypeList.get(filterTypeList.size() - 1);
        assertThat(testFilterType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFilterTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filterTypeRepository.findAll().size();

        // Create the FilterType with an existing ID
        filterType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterTypeMockMvc.perform(post("/api/filter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterType)))
            .andExpect(status().isBadRequest());

        // Validate the FilterType in the database
        List<FilterType> filterTypeList = filterTypeRepository.findAll();
        assertThat(filterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilterTypes() throws Exception {
        // Initialize the database
        filterTypeRepository.saveAndFlush(filterType);

        // Get all the filterTypeList
        restFilterTypeMockMvc.perform(get("/api/filter-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFilterType() throws Exception {
        // Initialize the database
        filterTypeRepository.saveAndFlush(filterType);

        // Get the filterType
        restFilterTypeMockMvc.perform(get("/api/filter-types/{id}", filterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filterType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilterType() throws Exception {
        // Get the filterType
        restFilterTypeMockMvc.perform(get("/api/filter-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilterType() throws Exception {
        // Initialize the database
        filterTypeRepository.saveAndFlush(filterType);
        int databaseSizeBeforeUpdate = filterTypeRepository.findAll().size();

        // Update the filterType
        FilterType updatedFilterType = filterTypeRepository.findOne(filterType.getId());
        updatedFilterType
            .name(UPDATED_NAME);

        restFilterTypeMockMvc.perform(put("/api/filter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilterType)))
            .andExpect(status().isOk());

        // Validate the FilterType in the database
        List<FilterType> filterTypeList = filterTypeRepository.findAll();
        assertThat(filterTypeList).hasSize(databaseSizeBeforeUpdate);
        FilterType testFilterType = filterTypeList.get(filterTypeList.size() - 1);
        assertThat(testFilterType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFilterType() throws Exception {
        int databaseSizeBeforeUpdate = filterTypeRepository.findAll().size();

        // Create the FilterType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilterTypeMockMvc.perform(put("/api/filter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterType)))
            .andExpect(status().isCreated());

        // Validate the FilterType in the database
        List<FilterType> filterTypeList = filterTypeRepository.findAll();
        assertThat(filterTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilterType() throws Exception {
        // Initialize the database
        filterTypeRepository.saveAndFlush(filterType);
        int databaseSizeBeforeDelete = filterTypeRepository.findAll().size();

        // Get the filterType
        restFilterTypeMockMvc.perform(delete("/api/filter-types/{id}", filterType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilterType> filterTypeList = filterTypeRepository.findAll();
        assertThat(filterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterType.class);
        FilterType filterType1 = new FilterType();
        filterType1.setId(1L);
        FilterType filterType2 = new FilterType();
        filterType2.setId(filterType1.getId());
        assertThat(filterType1).isEqualTo(filterType2);
        filterType2.setId(2L);
        assertThat(filterType1).isNotEqualTo(filterType2);
        filterType1.setId(null);
        assertThat(filterType1).isNotEqualTo(filterType2);
    }
}
