package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.WaterTest;

import com.poranski.repository.WaterTestRepository;
import com.poranski.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WaterTest.
 */
@RestController
@RequestMapping("/api")
public class WaterTestResource {

    private final Logger log = LoggerFactory.getLogger(WaterTestResource.class);

    private static final String ENTITY_NAME = "waterTest";

    private final WaterTestRepository waterTestRepository;

    public WaterTestResource(WaterTestRepository waterTestRepository) {
        this.waterTestRepository = waterTestRepository;
    }

    /**
     * POST  /water-tests : Create a new waterTest.
     *
     * @param waterTest the waterTest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new waterTest, or with status 400 (Bad Request) if the waterTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/water-tests")
    @Timed
    public ResponseEntity<WaterTest> createWaterTest(@RequestBody WaterTest waterTest) throws URISyntaxException {
        log.debug("REST request to save WaterTest : {}", waterTest);
        if (waterTest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new waterTest cannot already have an ID")).body(null);
        }
        WaterTest result = waterTestRepository.save(waterTest);
        return ResponseEntity.created(new URI("/api/water-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /water-tests : Updates an existing waterTest.
     *
     * @param waterTest the waterTest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated waterTest,
     * or with status 400 (Bad Request) if the waterTest is not valid,
     * or with status 500 (Internal Server Error) if the waterTest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/water-tests")
    @Timed
    public ResponseEntity<WaterTest> updateWaterTest(@RequestBody WaterTest waterTest) throws URISyntaxException {
        log.debug("REST request to update WaterTest : {}", waterTest);
        if (waterTest.getId() == null) {
            return createWaterTest(waterTest);
        }
        WaterTest result = waterTestRepository.save(waterTest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, waterTest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /water-tests : get all the waterTests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of waterTests in body
     */
    @GetMapping("/water-tests")
    @Timed
    public List<WaterTest> getAllWaterTests() {
        log.debug("REST request to get all WaterTests");
        return waterTestRepository.findAll();
        }

    /**
     * GET  /water-tests/:id : get the "id" waterTest.
     *
     * @param id the id of the waterTest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the waterTest, or with status 404 (Not Found)
     */
    @GetMapping("/water-tests/{id}")
    @Timed
    public ResponseEntity<WaterTest> getWaterTest(@PathVariable Long id) {
        log.debug("REST request to get WaterTest : {}", id);
        WaterTest waterTest = waterTestRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(waterTest));
    }

    /**
     * DELETE  /water-tests/:id : delete the "id" waterTest.
     *
     * @param id the id of the waterTest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/water-tests/{id}")
    @Timed
    public ResponseEntity<Void> deleteWaterTest(@PathVariable Long id) {
        log.debug("REST request to delete WaterTest : {}", id);
        waterTestRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
