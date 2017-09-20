package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.FilterType;

import com.poranski.repository.FilterTypeRepository;
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
 * REST controller for managing FilterType.
 */
@RestController
@RequestMapping("/api")
public class FilterTypeResource {

    private final Logger log = LoggerFactory.getLogger(FilterTypeResource.class);

    private static final String ENTITY_NAME = "filterType";

    private final FilterTypeRepository filterTypeRepository;

    public FilterTypeResource(FilterTypeRepository filterTypeRepository) {
        this.filterTypeRepository = filterTypeRepository;
    }

    /**
     * POST  /filter-types : Create a new filterType.
     *
     * @param filterType the filterType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filterType, or with status 400 (Bad Request) if the filterType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filter-types")
    @Timed
    public ResponseEntity<FilterType> createFilterType(@RequestBody FilterType filterType) throws URISyntaxException {
        log.debug("REST request to save FilterType : {}", filterType);
        if (filterType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filterType cannot already have an ID")).body(null);
        }
        FilterType result = filterTypeRepository.save(filterType);
        return ResponseEntity.created(new URI("/api/filter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filter-types : Updates an existing filterType.
     *
     * @param filterType the filterType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filterType,
     * or with status 400 (Bad Request) if the filterType is not valid,
     * or with status 500 (Internal Server Error) if the filterType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filter-types")
    @Timed
    public ResponseEntity<FilterType> updateFilterType(@RequestBody FilterType filterType) throws URISyntaxException {
        log.debug("REST request to update FilterType : {}", filterType);
        if (filterType.getId() == null) {
            return createFilterType(filterType);
        }
        FilterType result = filterTypeRepository.save(filterType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filterType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filter-types : get all the filterTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filterTypes in body
     */
    @GetMapping("/filter-types")
    @Timed
    public List<FilterType> getAllFilterTypes() {
        log.debug("REST request to get all FilterTypes");
        return filterTypeRepository.findAll();
        }

    /**
     * GET  /filter-types/:id : get the "id" filterType.
     *
     * @param id the id of the filterType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filterType, or with status 404 (Not Found)
     */
    @GetMapping("/filter-types/{id}")
    @Timed
    public ResponseEntity<FilterType> getFilterType(@PathVariable Long id) {
        log.debug("REST request to get FilterType : {}", id);
        FilterType filterType = filterTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filterType));
    }

    /**
     * DELETE  /filter-types/:id : delete the "id" filterType.
     *
     * @param id the id of the filterType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filter-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilterType(@PathVariable Long id) {
        log.debug("REST request to delete FilterType : {}", id);
        filterTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
