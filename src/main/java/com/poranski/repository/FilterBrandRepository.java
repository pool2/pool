package com.poranski.repository;

import com.poranski.domain.FilterBrand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FilterBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterBrandRepository extends JpaRepository<FilterBrand, Long> {

}
