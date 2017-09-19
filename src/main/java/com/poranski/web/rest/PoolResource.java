package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.Pool;

import com.poranski.repository.PoolRepository;
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
 * REST controller for managing Pool.
 */
@RestController
@RequestMapping("/api")
public class PoolResource {

    private final Logger log = LoggerFactory.getLogger(PoolResource.class);

    private static final String ENTITY_NAME = "pool";

    private final PoolRepository poolRepository;

    public PoolResource(PoolRepository poolRepository) {
        this.poolRepository = poolRepository;
    }

    /**
     * POST  /pools : Create a new pool.
     *
     * @param pool the pool to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pool, or with status 400 (Bad Request) if the pool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pools")
    @Timed
    public ResponseEntity<Pool> createPool(@RequestBody Pool pool) throws URISyntaxException {
        log.debug("REST request to save Pool : {}", pool);
        if (pool.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pool cannot already have an ID")).body(null);
        }
        Pool result = poolRepository.save(pool);
        return ResponseEntity.created(new URI("/api/pools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pools : Updates an existing pool.
     *
     * @param pool the pool to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pool,
     * or with status 400 (Bad Request) if the pool is not valid,
     * or with status 500 (Internal Server Error) if the pool couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pools")
    @Timed
    public ResponseEntity<Pool> updatePool(@RequestBody Pool pool) throws URISyntaxException {
        log.debug("REST request to update Pool : {}", pool);
        if (pool.getId() == null) {
            return createPool(pool);
        }
        Pool result = poolRepository.save(pool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pool.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pools : get all the pools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pools in body
     */
    @GetMapping("/pools")
    @Timed
    public List<Pool> getAllPools() {
        log.debug("REST request to get all Pools");
        return poolRepository.findAll();
        }

    /**
     * GET  /pools/:id : get the "id" pool.
     *
     * @param id the id of the pool to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pool, or with status 404 (Not Found)
     */
    @GetMapping("/pools/{id}")
    @Timed
    public ResponseEntity<Pool> getPool(@PathVariable Long id) {
        log.debug("REST request to get Pool : {}", id);
        Pool pool = poolRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pool));
    }

    /**
     * DELETE  /pools/:id : delete the "id" pool.
     *
     * @param id the id of the pool to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pools/{id}")
    @Timed
    public ResponseEntity<Void> deletePool(@PathVariable Long id) {
        log.debug("REST request to delete Pool : {}", id);
        poolRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
