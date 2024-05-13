package com.Garage.vehicleownerservice.controller;

import com.Garage.vehicleownerservice.model.VehicleOwner;


import com.Garage.vehicleownerservice.service.VehicleOwnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/owner")
public class VehicleOwnerController {

   @Autowired
   private VehicleOwnerService vehicleOwnerService;

    @GetMapping("/getall")
    public List<VehicleOwner> getAllVehicleOwners() {
        return vehicleOwnerService.getAllVehicleOwners();
    }


    @PostMapping("/add")
    public ResponseEntity<String> addVehicleOwner(@RequestBody VehicleOwner vehicleOwner) {
//        return vehicleOwnerService.addVehicleOwner(vehicleOwner);
        return vehicleOwnerService.addVehicleOwner(vehicleOwner);
    }

//    @GetMapping("/getbyid/{id}")
//    public VehicleOwner getbyid(@PathVariable Long id){
//        return vehicleOwnerService.getbyid(id);
//    }

//@GetMapping("/getbyid/{id}")
//public ResponseEntity<VehicleOwner> getbyid(@PathVariable Long id) {
//    try {
//        VehicleOwner owner = vehicleOwnerService.getbyid(id);
//        return new ResponseEntity<>(owner, HttpStatus.OK);
//    } catch (RuntimeException e) {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}

    @GetMapping("/{owner_id}")
    public Optional<VehicleOwner> getbyid(@PathVariable Integer userId){
        return vehicleOwnerService.getbyid(userId);
    }
//    @GetMapping("/email")
//    public ResponseEntity<String> getEmailFromToken(@RequestHeader("Authorization") String token) {
//        String email = vehicleOwnerService.getUserEmailFromToken(token.replace("Bearer ", ""));
//        if (email != null) {
//            return ResponseEntity.ok(email);
//        } else {
//            return ResponseEntity.badRequest().body("Invalid or missing token");
//        }
//    }

    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<VehicleOwner> getByEmail(@PathVariable String email) {
        VehicleOwner owner = vehicleOwnerService.getByEmail(email);
        if (owner != null) {
            return ResponseEntity.ok(owner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//




//
    @PutMapping("/edit/{id}")
    public VehicleOwner editOwner(@PathVariable Integer id, @RequestBody VehicleOwner vehicleOwner) {
        return vehicleOwnerService.editOwner(id, vehicleOwner);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOwner(@PathVariable Integer id) {
        return vehicleOwnerService.deleteOwnerById(id);
    }
}
