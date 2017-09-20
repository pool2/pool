package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.FilterBrand;
import com.poranski.repository.FilterBrandRepository;
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
 * Test class for the FilterBrandResource REST controller.
 *
 * @see FilterBrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class FilterBrandResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FilterBrandRepository filterBrandRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilterBrandMockMvc;

    private FilterBrand filterBrand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilterBrandResource filterBrandResource = new FilterBrandResource(filterBrandRepository);
        this.restFilterBrandMockMvc = MockMvcBuilders.standaloneSetup(filterBrandResource)
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
    public static FilterBrand createEntity(EntityManager em) {
        FilterBrand filterBrand = new FilterBrand()
            .name(DEFAULT_NAME);
        return filterBrand;
    }

    @Before
    public void initTest() {
        filterBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilterBrand() throws Exception {
        int databaseSizeBeforeCreate = filterBrandRepository.findAll().size();

        // Create the FilterBrand
        restFilterBrandMockMvc.perform(post("/api/filter-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterBrand)))
            .andExpect(status().isCreated());

        // Validate the FilterBrand in the database
        List<FilterBrand> filterBrandList = filterBrandRepository.findAll();
        assertThat(filterBrandList).hasSize(databaseSizeBeforeCreate + 1);
        FilterBrand testFilterBrand = filterBrandList.get(filterBrandList.size() - 1);
        assertThat(testFilterBrand.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFilterBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filterBrandRepository.findAll().size();

        // Create the FilterBrand with an existing ID
        filterBrand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterBrandMockMvc.perform(post("/api/filter-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterBrand)))
            .andExpect(status().isBadRequest());

        // Validate the FilterBrand in the database
        List<FilterBrand> filterBrandList = filterBrandRepository.findAll();
        assertThat(filterBrandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilterBrands() throws Exception {
        // Initialize the database
        filterBrandRepository.saveAndFlush(filterBrand);

        // Get all the filterBrandList
        restFilterBrandMockMvc.perform(get("/api/filter-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFilterBrand() throws Exception {
        // Initialize the database
        filterBrandRepository.saveAndFlush(filterBrand);

        // Get the filterBrand
        restFilterBrandMockMvc.perform(get("/api/filter-brands/{id}", filterBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filterBrand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilterBrand() throws Exception {
        // Get the filterBrand
        restFilterBrandMockMvc.perform(get("/api/filter-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilterBrand() throws Exception {
        // Initialize the database
        filterBrandRepository.saveAndFlush(filterBrand);
        int databaseSizeBeforeUpdate = filterBrandRepository.findAll().size();

        // Update the filterBrand
        FilterBrand updatedFilterBrand = filterBrandRepository.findOne(filterBrand.getId());
        updatedFilterBrand
            .name(UPDATED_NAME);

        restFilterBrandMockMvc.perform(put("/api/filter-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilterBrand)))
            .andExpect(status().isOk());

        // Validate the FilterBrand in the database
        List<FilterBrand> filterBrandList = filterBrandRepository.findAll();
        assertThat(filterBrandList).hasSize(databaseSizeBeforeUpdate);
        FilterBrand testFilterBrand = filterBrandList.get(filterBrandList.size() - 1);
        assertThat(testFilterBrand.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFilterBrand() throws Exception {
        int databaseSizeBeforeUpdate = filterBrandRepository.findAll().size();

        // Create the FilterBrand

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilterBrandMockMvc.perform(put("/api/filter-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterBrand)))
            .andExpect(status().isCreated());

        // Validate the FilterBrand in the database
        List<FilterBrand> filterBrandList = filterBrandRepository.findAll();
        assertThat(filterBrandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilterBrand() throws Exception {
        // Initialize the database
        filterBrandRepository.saveAndFlush(filterBrand);
        int databaseSizeBeforeDelete = filterBrandRepository.findAll().size();

        // Get the filterBrand
        restFilterBrandMockMvc.perform(delete("/api/filter-brands/{id}", filterBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilterBrand> filterBrandList = filterBrandRepository.findAll();
        assertThat(filterBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterBrand.class);
        FilterBrand filterBrand1 = new FilterBrand();
        filterBrand1.setId(1L);
        FilterBrand filterBrand2 = new FilterBrand();
        filterBrand2.setId(filterBrand1.getId());
        assertThat(filterBrand1).isEqualTo(filterBrand2);
        filterBrand2.setId(2L);
        assertThat(filterBrand1).isNotEqualTo(filterBrand2);
        filterBrand1.setId(null);
        assertThat(filterBrand1).isNotEqualTo(filterBrand2);
    }
}
