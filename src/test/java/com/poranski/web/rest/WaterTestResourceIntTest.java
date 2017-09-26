package com.poranski.web.rest;

import com.poranski.PoolApp;

import com.poranski.domain.WaterTest;
import com.poranski.repository.WaterTestRepository;
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
 * Test class for the WaterTestResource REST controller.
 *
 * @see WaterTestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoolApp.class)
public class WaterTestResourceIntTest {

    private static final Float DEFAULT_PH = 1F;
    private static final Float UPDATED_PH = 2F;

    private static final Float DEFAULT_CHLORINE = 1F;
    private static final Float UPDATED_CHLORINE = 2F;

    private static final Float DEFAULT_TOTAL_ALKALINITY = 1F;
    private static final Float UPDATED_TOTAL_ALKALINITY = 2F;

    private static final Float DEFAULT_CALCIUM_HARDNESS = 1F;
    private static final Float UPDATED_CALCIUM_HARDNESS = 2F;

    private static final Float DEFAULT_CYANURIC_ACID = 1F;
    private static final Float UPDATED_CYANURIC_ACID = 2F;

    private static final Float DEFAULT_TOTAL_DISSOLVED_SOLIDS = 1F;
    private static final Float UPDATED_TOTAL_DISSOLVED_SOLIDS = 2F;

    private static final LocalDate DEFAULT_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private WaterTestRepository waterTestRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWaterTestMockMvc;

    private WaterTest waterTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WaterTestResource waterTestResource = new WaterTestResource(waterTestRepository);
        this.restWaterTestMockMvc = MockMvcBuilders.standaloneSetup(waterTestResource)
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
    public static WaterTest createEntity(EntityManager em) {
        WaterTest waterTest = new WaterTest()
            .ph(DEFAULT_PH)
            .chlorine(DEFAULT_CHLORINE)
            .totalAlkalinity(DEFAULT_TOTAL_ALKALINITY)
            .calciumHardness(DEFAULT_CALCIUM_HARDNESS)
            .cyanuricAcid(DEFAULT_CYANURIC_ACID)
            .totalDissolvedSolids(DEFAULT_TOTAL_DISSOLVED_SOLIDS)
            .dateTime(DEFAULT_DATE_TIME)
            .note(DEFAULT_NOTE);
        return waterTest;
    }

    @Before
    public void initTest() {
        waterTest = createEntity(em);
    }

    @Test
    @Transactional
    public void createWaterTest() throws Exception {
        int databaseSizeBeforeCreate = waterTestRepository.findAll().size();

        // Create the WaterTest
        restWaterTestMockMvc.perform(post("/api/water-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waterTest)))
            .andExpect(status().isCreated());

        // Validate the WaterTest in the database
        List<WaterTest> waterTestList = waterTestRepository.findAll();
        assertThat(waterTestList).hasSize(databaseSizeBeforeCreate + 1);
        WaterTest testWaterTest = waterTestList.get(waterTestList.size() - 1);
        assertThat(testWaterTest.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testWaterTest.getChlorine()).isEqualTo(DEFAULT_CHLORINE);
        assertThat(testWaterTest.getTotalAlkalinity()).isEqualTo(DEFAULT_TOTAL_ALKALINITY);
        assertThat(testWaterTest.getCalciumHardness()).isEqualTo(DEFAULT_CALCIUM_HARDNESS);
        assertThat(testWaterTest.getCyanuricAcid()).isEqualTo(DEFAULT_CYANURIC_ACID);
        assertThat(testWaterTest.getTotalDissolvedSolids()).isEqualTo(DEFAULT_TOTAL_DISSOLVED_SOLIDS);
        assertThat(testWaterTest.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testWaterTest.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createWaterTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = waterTestRepository.findAll().size();

        // Create the WaterTest with an existing ID
        waterTest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaterTestMockMvc.perform(post("/api/water-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waterTest)))
            .andExpect(status().isBadRequest());

        // Validate the WaterTest in the database
        List<WaterTest> waterTestList = waterTestRepository.findAll();
        assertThat(waterTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWaterTests() throws Exception {
        // Initialize the database
        waterTestRepository.saveAndFlush(waterTest);

        // Get all the waterTestList
        restWaterTestMockMvc.perform(get("/api/water-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waterTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.doubleValue())))
            .andExpect(jsonPath("$.[*].chlorine").value(hasItem(DEFAULT_CHLORINE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAlkalinity").value(hasItem(DEFAULT_TOTAL_ALKALINITY.doubleValue())))
            .andExpect(jsonPath("$.[*].calciumHardness").value(hasItem(DEFAULT_CALCIUM_HARDNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].cyanuricAcid").value(hasItem(DEFAULT_CYANURIC_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDissolvedSolids").value(hasItem(DEFAULT_TOTAL_DISSOLVED_SOLIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getWaterTest() throws Exception {
        // Initialize the database
        waterTestRepository.saveAndFlush(waterTest);

        // Get the waterTest
        restWaterTestMockMvc.perform(get("/api/water-tests/{id}", waterTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(waterTest.getId().intValue()))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.doubleValue()))
            .andExpect(jsonPath("$.chlorine").value(DEFAULT_CHLORINE.doubleValue()))
            .andExpect(jsonPath("$.totalAlkalinity").value(DEFAULT_TOTAL_ALKALINITY.doubleValue()))
            .andExpect(jsonPath("$.calciumHardness").value(DEFAULT_CALCIUM_HARDNESS.doubleValue()))
            .andExpect(jsonPath("$.cyanuricAcid").value(DEFAULT_CYANURIC_ACID.doubleValue()))
            .andExpect(jsonPath("$.totalDissolvedSolids").value(DEFAULT_TOTAL_DISSOLVED_SOLIDS.doubleValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWaterTest() throws Exception {
        // Get the waterTest
        restWaterTestMockMvc.perform(get("/api/water-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWaterTest() throws Exception {
        // Initialize the database
        waterTestRepository.saveAndFlush(waterTest);
        int databaseSizeBeforeUpdate = waterTestRepository.findAll().size();

        // Update the waterTest
        WaterTest updatedWaterTest = waterTestRepository.findOne(waterTest.getId());
        updatedWaterTest
            .ph(UPDATED_PH)
            .chlorine(UPDATED_CHLORINE)
            .totalAlkalinity(UPDATED_TOTAL_ALKALINITY)
            .calciumHardness(UPDATED_CALCIUM_HARDNESS)
            .cyanuricAcid(UPDATED_CYANURIC_ACID)
            .totalDissolvedSolids(UPDATED_TOTAL_DISSOLVED_SOLIDS)
            .dateTime(UPDATED_DATE_TIME)
            .note(UPDATED_NOTE);

        restWaterTestMockMvc.perform(put("/api/water-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWaterTest)))
            .andExpect(status().isOk());

        // Validate the WaterTest in the database
        List<WaterTest> waterTestList = waterTestRepository.findAll();
        assertThat(waterTestList).hasSize(databaseSizeBeforeUpdate);
        WaterTest testWaterTest = waterTestList.get(waterTestList.size() - 1);
        assertThat(testWaterTest.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testWaterTest.getChlorine()).isEqualTo(UPDATED_CHLORINE);
        assertThat(testWaterTest.getTotalAlkalinity()).isEqualTo(UPDATED_TOTAL_ALKALINITY);
        assertThat(testWaterTest.getCalciumHardness()).isEqualTo(UPDATED_CALCIUM_HARDNESS);
        assertThat(testWaterTest.getCyanuricAcid()).isEqualTo(UPDATED_CYANURIC_ACID);
        assertThat(testWaterTest.getTotalDissolvedSolids()).isEqualTo(UPDATED_TOTAL_DISSOLVED_SOLIDS);
        assertThat(testWaterTest.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testWaterTest.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingWaterTest() throws Exception {
        int databaseSizeBeforeUpdate = waterTestRepository.findAll().size();

        // Create the WaterTest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWaterTestMockMvc.perform(put("/api/water-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waterTest)))
            .andExpect(status().isCreated());

        // Validate the WaterTest in the database
        List<WaterTest> waterTestList = waterTestRepository.findAll();
        assertThat(waterTestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWaterTest() throws Exception {
        // Initialize the database
        waterTestRepository.saveAndFlush(waterTest);
        int databaseSizeBeforeDelete = waterTestRepository.findAll().size();

        // Get the waterTest
        restWaterTestMockMvc.perform(delete("/api/water-tests/{id}", waterTest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WaterTest> waterTestList = waterTestRepository.findAll();
        assertThat(waterTestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WaterTest.class);
        WaterTest waterTest1 = new WaterTest();
        waterTest1.setId(1L);
        WaterTest waterTest2 = new WaterTest();
        waterTest2.setId(waterTest1.getId());
        assertThat(waterTest1).isEqualTo(waterTest2);
        waterTest2.setId(2L);
        assertThat(waterTest1).isNotEqualTo(waterTest2);
        waterTest1.setId(null);
        assertThat(waterTest1).isNotEqualTo(waterTest2);
    }
}
