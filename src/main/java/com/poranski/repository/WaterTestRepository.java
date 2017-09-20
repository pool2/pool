package com.poranski.repository;

import com.poranski.domain.WaterTest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WaterTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaterTestRepository extends JpaRepository<WaterTest, Long> {

}
