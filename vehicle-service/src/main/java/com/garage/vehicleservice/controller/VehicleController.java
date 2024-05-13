package com.garage.vehicleservice.controller;

import com.garage.vehicleservice.model.Vehicle;
import com.garage.vehicleservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
@Autowired
private VehicleService vehicleService;

    @GetMapping("/getall")
    public List<Vehicle> getAllVehicles() {

        return vehicleService.getAllVehicles();
    }



    @PostMapping("/add")
    public Vehicle addAVehicle(@RequestBody AddVehicleRequest request) {
        Long ownerId = request.getOwnerId();
        Vehicle vehicle = request.getVehicle();

        // Set ownerId in the vehicle
        vehicle.setOwnerId(ownerId);

        return vehicleService.addAVehicle(vehicle);
    }

    @PutMapping("/edit/{id}")
    public Vehicle update(@PathVariable Long id,@RequestBody Vehicle vehicle){
       return vehicleService.edit(id,vehicle) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String result = vehicleService.deletebyid(id);
        if (result.equals("Deleted Successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    public static class AddVehicleRequest {
        private Long ownerId;
        private Vehicle vehicle;

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }
    }
    @GetMapping("/owner/{ownerId}")
    public List<Vehicle> getVehiclesByOwnerId(@PathVariable Long ownerId) {
        return vehicleService.getVehiclesByOwnerId(ownerId);
    }



}
