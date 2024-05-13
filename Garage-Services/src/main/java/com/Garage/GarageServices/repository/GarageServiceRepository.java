package com.Garage.GarageServices.repository;

import com.Garage.GarageServices.model.GarageServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarageServiceRepository extends JpaRepository<GarageServiceModel,Long> {


}
