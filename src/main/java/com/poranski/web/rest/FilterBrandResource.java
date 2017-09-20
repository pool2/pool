package com.poranski.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poranski.domain.FilterBrand;

import com.poranski.repository.FilterBrandRepository;
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
 * REST controller for managing FilterBrand.
 */
@RestController
@RequestMapping("/api")
public class FilterBrandResource {

    private final Logger log = LoggerFactory.getLogger(FilterBrandResource.class);

    private static final String ENTITY_NAME = "filterBrand";

    private final FilterBrandRepository filterBrandRepository;

    public FilterBrandResource(FilterBrandRepository filterBrandRepository) {
        this.filterBrandRepository = filterBrandRepository;
    }

    /**
     * POST  /filter-brands : Create a new filterBrand.
     *
     * @param filterBrand the filterBrand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filterBrand, or with status 400 (Bad Request) if the filterBrand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filter-brands")
    @Timed
    public ResponseEntity<FilterBrand> createFilterBrand(@RequestBody FilterBrand filterBrand) throws URISyntaxException {
        log.debug("REST request to save FilterBrand : {}", filterBrand);
        if (filterBrand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filterBrand cannot already have an ID")).body(null);
        }
        FilterBrand result = filterBrandRepository.save(filterBrand);
        return ResponseEntity.created(new URI("/api/filter-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filter-brands : Updates an existing filterBrand.
     *
     * @param filterBrand the filterBrand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filterBrand,
     * or with status 400 (Bad Request) if the filterBrand is not valid,
     * or with status 500 (Internal Server Error) if the filterBrand couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filter-brands")
    @Timed
    public ResponseEntity<FilterBrand> updateFilterBrand(@RequestBody FilterBrand filterBrand) throws URISyntaxException {
        log.debug("REST request to update FilterBrand : {}", filterBrand);
        if (filterBrand.getId() == null) {
            return createFilterBrand(filterBrand);
        }
        FilterBrand result = filterBrandRepository.save(filterBrand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filterBrand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filter-brands : get all the filterBrands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filterBrands in body
     */
    @GetMapping("/filter-brands")
    @Timed
    public List<FilterBrand> getAllFilterBrands() {
        log.debug("REST request to get all FilterBrands");
        return filterBrandRepository.findAll();
        }

    /**
     * GET  /filter-brands/:id : get the "id" filterBrand.
     *
     * @param id the id of the filterBrand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filterBrand, or with status 404 (Not Found)
     */
    @GetMapping("/filter-brands/{id}")
    @Timed
    public ResponseEntity<FilterBrand> getFilterBrand(@PathVariable Long id) {
        log.debug("REST request to get FilterBrand : {}", id);
        FilterBrand filterBrand = filterBrandRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filterBrand));
    }

    /**
     * DELETE  /filter-brands/:id : delete the "id" filterBrand.
     *
     * @param id the id of the filterBrand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filter-brands/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilterBrand(@PathVariable Long id) {
        log.debug("REST request to delete FilterBrand : {}", id);
        filterBrandRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
