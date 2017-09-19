package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.InventoryItem;

import com.poranski.repository.InventoryItemRepository;
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
 * REST controller for managing InventoryItem.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemResource.class);

    private static final String ENTITY_NAME = "inventoryItem";

    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemResource(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    /**
     * POST  /inventory-items : Create a new inventoryItem.
     *
     * @param inventoryItem the inventoryItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryItem, or with status 400 (Bad Request) if the inventoryItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventory-items")
    @Timed
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem inventoryItem) throws URISyntaxException {
        log.debug("REST request to save InventoryItem : {}", inventoryItem);
        if (inventoryItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inventoryItem cannot already have an ID")).body(null);
        }
        InventoryItem result = inventoryItemRepository.save(inventoryItem);
        return ResponseEntity.created(new URI("/api/inventory-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventory-items : Updates an existing inventoryItem.
     *
     * @param inventoryItem the inventoryItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventoryItem,
     * or with status 400 (Bad Request) if the inventoryItem is not valid,
     * or with status 500 (Internal Server Error) if the inventoryItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventory-items")
    @Timed
    public ResponseEntity<InventoryItem> updateInventoryItem(@RequestBody InventoryItem inventoryItem) throws URISyntaxException {
        log.debug("REST request to update InventoryItem : {}", inventoryItem);
        if (inventoryItem.getId() == null) {
            return createInventoryItem(inventoryItem);
        }
        InventoryItem result = inventoryItemRepository.save(inventoryItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventoryItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventory-items : get all the inventoryItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inventoryItems in body
     */
    @GetMapping("/inventory-items")
    @Timed
    public List<InventoryItem> getAllInventoryItems() {
        log.debug("REST request to get all InventoryItems");
        return inventoryItemRepository.findAll();
        }

    /**
     * GET  /inventory-items/:id : get the "id" inventoryItem.
     *
     * @param id the id of the inventoryItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryItem, or with status 404 (Not Found)
     */
    @GetMapping("/inventory-items/{id}")
    @Timed
    public ResponseEntity<InventoryItem> getInventoryItem(@PathVariable Long id) {
        log.debug("REST request to get InventoryItem : {}", id);
        InventoryItem inventoryItem = inventoryItemRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventoryItem));
    }

    /**
     * DELETE  /inventory-items/:id : delete the "id" inventoryItem.
     *
     * @param id the id of the inventoryItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItem : {}", id);
        inventoryItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
