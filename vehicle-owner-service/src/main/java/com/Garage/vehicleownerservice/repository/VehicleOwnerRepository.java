package com.Garage.vehicleownerservice.repository;

import com.Garage.vehicleownerservice.model.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Integer>{
    VehicleOwner findByEmail(String email);
    VehicleOwner findFirstByOrderByUserIdDesc();
}
