package com.poranski.repository;

import com.poranski.domain.InventoryUsed;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InventoryUsed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryUsedRepository extends JpaRepository<InventoryUsed, Long> {

}
