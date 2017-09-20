package com.poranski.repository;

import com.poranski.domain.Employee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select e from Employee e where e.user.login = ?1")
    Employee findOneByUserLogin(String login);
}
