package com.poranski.repository;

import com.poranski.domain.InventoryItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InventoryItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

}
