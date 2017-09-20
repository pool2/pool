package com.poranski.repository;

import com.poranski.domain.Appointment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select distinct appointment from Appointment appointment left join fetch appointment.inventoryUseds left join fetch appointment.tasks")
    List<Appointment> findAllWithEagerRelationships();

    @Query("select appointment from Appointment appointment left join fetch appointment.inventoryUseds left join fetch appointment.tasks where appointment.id =:id")
    Appointment findOneWithEagerRelationships(@Param("id") Long id);

}
