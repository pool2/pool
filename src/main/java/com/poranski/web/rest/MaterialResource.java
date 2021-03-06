package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.Material;

import com.poranski.repository.MaterialRepository;
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
 * REST controller for managing Material.
 */
@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static final String ENTITY_NAME = "material";

    private final MaterialRepository materialRepository;

    public MaterialResource(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * POST  /materials : Create a new material.
     *
     * @param material the material to create
     * @return the ResponseEntity with status 201 (Created) and with body the new material, or with status 400 (Bad Request) if the material has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materials")
    @Timed
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);
        if (material.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new material cannot already have an ID")).body(null);
        }
        Material result = materialRepository.save(material);
        return ResponseEntity.created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /materials : Updates an existing material.
     *
     * @param material the material to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated material,
     * or with status 400 (Bad Request) if the material is not valid,
     * or with status 500 (Internal Server Error) if the material couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materials")
    @Timed
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to update Material : {}", material);
        if (material.getId() == null) {
            return createMaterial(material);
        }
        Material result = materialRepository.save(material);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, material.getId().toString()))
            .body(result);
    }

    /**
     * GET  /materials : get all the materials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of materials in body
     */
    @GetMapping("/materials")
    @Timed
    public List<Material> getAllMaterials() {
        log.debug("REST request to get all Materials");
        return materialRepository.findAll();
        }

    /**
     * GET  /materials/:id : get the "id" material.
     *
     * @param id the id of the material to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the material, or with status 404 (Not Found)
     */
    @GetMapping("/materials/{id}")
    @Timed
    public ResponseEntity<Material> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Material material = materialRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(material));
    }

    /**
     * DELETE  /materials/:id : delete the "id" material.
     *
     * @param id the id of the material to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
