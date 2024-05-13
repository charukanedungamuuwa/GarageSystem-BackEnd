package com.Garage.AppoinmentService.repository;

import com.Garage.AppoinmentService.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository <Appointment, Long> {
    List<Appointment> findByOwnerId(Long ownerId);


    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.vehicleId = :vehicleId")
    boolean hasAppointmentsForVehicle(@Param("vehicleId") Long vehicleId);
}
