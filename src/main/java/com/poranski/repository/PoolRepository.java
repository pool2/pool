package com.poranski.repository;

import com.poranski.domain.Pool;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Pool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {

    @Query(value = "select p from Pool p where p.customer.id = ?1")
    List<Pool> findByCustomerId(Long customerId);
}
