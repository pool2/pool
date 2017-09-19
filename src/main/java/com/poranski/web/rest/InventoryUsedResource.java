package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.InventoryUsed;

import com.poranski.repository.InventoryUsedRepository;
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
 * REST controller for managing InventoryUsed.
 */
@RestController
@RequestMapping("/api")
public class InventoryUsedResource {

    private final Logger log = LoggerFactory.getLogger(InventoryUsedResource.class);

    private static final String ENTITY_NAME = "inventoryUsed";

    private final InventoryUsedRepository inventoryUsedRepository;

    public InventoryUsedResource(InventoryUsedRepository inventoryUsedRepository) {
        this.inventoryUsedRepository = inventoryUsedRepository;
    }

    /**
     * POST  /inventory-useds : Create a new inventoryUsed.
     *
     * @param inventoryUsed the inventoryUsed to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryUsed, or with status 400 (Bad Request) if the inventoryUsed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventory-useds")
    @Timed
    public ResponseEntity<InventoryUsed> createInventoryUsed(@RequestBody InventoryUsed inventoryUsed) throws URISyntaxException {
        log.debug("REST request to save InventoryUsed : {}", inventoryUsed);
        if (inventoryUsed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inventoryUsed cannot already have an ID")).body(null);
        }
        InventoryUsed result = inventoryUsedRepository.save(inventoryUsed);
        return ResponseEntity.created(new URI("/api/inventory-useds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventory-useds : Updates an existing inventoryUsed.
     *
     * @param inventoryUsed the inventoryUsed to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventoryUsed,
     * or with status 400 (Bad Request) if the inventoryUsed is not valid,
     * or with status 500 (Internal Server Error) if the inventoryUsed couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventory-useds")
    @Timed
    public ResponseEntity<InventoryUsed> updateInventoryUsed(@RequestBody InventoryUsed inventoryUsed) throws URISyntaxException {
        log.debug("REST request to update InventoryUsed : {}", inventoryUsed);
        if (inventoryUsed.getId() == null) {
            return createInventoryUsed(inventoryUsed);
        }
        InventoryUsed result = inventoryUsedRepository.save(inventoryUsed);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventoryUsed.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventory-useds : get all the inventoryUseds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inventoryUseds in body
     */
    @GetMapping("/inventory-useds")
    @Timed
    public List<InventoryUsed> getAllInventoryUseds() {
        log.debug("REST request to get all InventoryUseds");
        return inventoryUsedRepository.findAll();
        }

    /**
     * GET  /inventory-useds/:id : get the "id" inventoryUsed.
     *
     * @param id the id of the inventoryUsed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryUsed, or with status 404 (Not Found)
     */
    @GetMapping("/inventory-useds/{id}")
    @Timed
    public ResponseEntity<InventoryUsed> getInventoryUsed(@PathVariable Long id) {
        log.debug("REST request to get InventoryUsed : {}", id);
        InventoryUsed inventoryUsed = inventoryUsedRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventoryUsed));
    }

    /**
     * DELETE  /inventory-useds/:id : delete the "id" inventoryUsed.
     *
     * @param id the id of the inventoryUsed to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-useds/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventoryUsed(@PathVariable Long id) {
        log.debug("REST request to delete InventoryUsed : {}", id);
        inventoryUsedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
