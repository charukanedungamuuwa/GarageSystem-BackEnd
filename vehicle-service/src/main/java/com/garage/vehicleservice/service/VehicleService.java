package com.garage.vehicleservice.service;


import com.garage.vehicleservice.model.Vehicle;
import com.garage.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
   private VehicleRepository vehicleRepository;




    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }
     public Vehicle addAVehicle(Vehicle vehicle){
         Vehicle existingVehicle = vehicleRepository.findByLicensePlate(vehicle.getLicensePlate());
         if (existingVehicle != null) {
             // If a vehicle with the same license plate exists, return null or throw an exception
             // You can handle this case based on your requirement
             throw new IllegalArgumentException("A vehicle with the same license plate already exists.");
         }
//         if (vehicle.getVehicleOwner() != null) {
//
//             VehicleOwner owner= vehicleOwnerFeignClient.getbyid(vehicle.getVehicleOwner().getOwner_id());
//
//             vehicle.setVehicleOwner(owner);
//         }
         //
        return vehicleRepository.save(vehicle);
     }




    public String deletebyid(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
        if (vehicle == null) {
            return "Vehicle not found with id: " + id;
        }
        RestTemplate restTemplate = new RestTemplate();
        // Make a GET request to the AppointmentService to check for appointments
        ResponseEntity<Boolean> response = restTemplate.getForEntity(
                "http://localhost:8086/api/appointments/hasAppointments/{vehicleId}",
                Boolean.class,
                id);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody()) {
            // There are appointments associated with the vehicle
            return "Cannot delete the vehicle. There are appointments associated with it.";
        }

        // No appointments found, proceed with deletion
        vehicleRepository.delete(vehicle);
        return "Deleted Successfully";
    }


    //


    //

    public Vehicle edit(Long id, Vehicle newVehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElse(null);
        if (existingVehicle == null) {
            throw new IllegalArgumentException("Vehicle not found with id: " + id);
        }

        RestTemplate restTemplate = new RestTemplate();
        // Make a GET request to the AppointmentService to check for appointments
        ResponseEntity<Boolean> response = restTemplate.getForEntity(
                "http://localhost:8086/api/appointments/hasAppointments/{vehicleId}",
                Boolean.class,
                id);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody()) {
            // There are appointments associated with the vehicle
            throw new IllegalArgumentException("Cannot edit the vehicle. There are appointments associated with it.");
        }

        existingVehicle.setYear(newVehicle.getYear());
        existingVehicle.setType(newVehicle.getType());
        existingVehicle.setLicensePlate(newVehicle.getLicensePlate());
        existingVehicle.setModel(newVehicle.getModel());

        return vehicleRepository.save(existingVehicle);
    }
//


    public List<Vehicle> getVehiclesByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }


}
