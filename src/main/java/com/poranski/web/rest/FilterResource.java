package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.Filter;

import com.poranski.repository.FilterRepository;
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
 * REST controller for managing Filter.
 */
@RestController
@RequestMapping("/api")
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(FilterResource.class);

    private static final String ENTITY_NAME = "filter";

    private final FilterRepository filterRepository;

    public FilterResource(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    /**
     * POST  /filters : Create a new filter.
     *
     * @param filter the filter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filter, or with status 400 (Bad Request) if the filter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filters")
    @Timed
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) throws URISyntaxException {
        log.debug("REST request to save Filter : {}", filter);
        if (filter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filter cannot already have an ID")).body(null);
        }
        Filter result = filterRepository.save(filter);
        return ResponseEntity.created(new URI("/api/filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filters : Updates an existing filter.
     *
     * @param filter the filter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filter,
     * or with status 400 (Bad Request) if the filter is not valid,
     * or with status 500 (Internal Server Error) if the filter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filters")
    @Timed
    public ResponseEntity<Filter> updateFilter(@RequestBody Filter filter) throws URISyntaxException {
        log.debug("REST request to update Filter : {}", filter);
        if (filter.getId() == null) {
            return createFilter(filter);
        }
        Filter result = filterRepository.save(filter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filters : get all the filters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filters in body
     */
    @GetMapping("/filters")
    @Timed
    public List<Filter> getAllFilters() {
        log.debug("REST request to get all Filters");
        return filterRepository.findAll();
        }

    /**
     * GET  /filters/:id : get the "id" filter.
     *
     * @param id the id of the filter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filter, or with status 404 (Not Found)
     */
    @GetMapping("/filters/{id}")
    @Timed
    public ResponseEntity<Filter> getFilter(@PathVariable Long id) {
        log.debug("REST request to get Filter : {}", id);
        Filter filter = filterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filter));
    }

    /**
     * DELETE  /filters/:id : delete the "id" filter.
     *
     * @param id the id of the filter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filters/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        log.debug("REST request to delete Filter : {}", id);
        filterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
