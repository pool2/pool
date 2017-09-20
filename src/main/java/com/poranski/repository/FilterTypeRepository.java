package com.poranski.repository;

import com.poranski.domain.FilterType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FilterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterTypeRepository extends JpaRepository<FilterType, Long> {

}
