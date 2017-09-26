package com.poranski.repository;

import com.poranski.domain.Customer;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select customer from Customer customer where customer.company.id = :id")
    List<Customer> findByCompanyId(@Param("id") Long id);
}
