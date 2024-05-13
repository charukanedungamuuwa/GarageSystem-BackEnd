package com.garage.vehicleservice.repository;

import com.garage.vehicleservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByOwnerId(Long ownerId);
    Vehicle findByLicensePlate(String licensePlate);
}
