package com.Garage.GarageServices.controller;

import com.Garage.GarageServices.kafka.ProducerService;
import com.Garage.GarageServices.model.GarageServiceModel;
import com.Garage.GarageServices.model.ServiceTimeSlot;
import com.Garage.GarageServices.service.GarageServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/services")
public class GargeServiceController {
    @Autowired
    private GarageServiceService garageServiceService;
    @Autowired
    private ProducerService producerService;
    @GetMapping("/all")
    public List<GarageServiceModel> getAllServices() {
        return garageServiceService.getAllServices();
    }

    @PostMapping("/api/add-garage-service")
    public ResponseEntity<String> addGarageService(@RequestBody GarageServiceModel garageService) {
        producerService.sendGarageServiceToKafka(garageService);
        return new ResponseEntity<>("Garage Service added to Kafka topic", HttpStatus.OK);
    }

    @GetMapping("/a")
    public String produceMessages() {
        producerService.produceGarageServiceModels();
        return "Messages produced successfully!";
    }
    @PostMapping("/add")
    public GarageServiceModel addService(@RequestBody GarageServiceModel service) {
        return garageServiceService.addService(service);
    }

    @PutMapping("/edit/{id}")
    public GarageServiceModel editService(@PathVariable Long id, @RequestBody GarageServiceModel updatedService) {
        return garageServiceService.editService(id, updatedService);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        return garageServiceService.deleteService(id);
    }
    @GetMapping("/getbyid/{id}")
    public Optional<GarageServiceModel> getById(@PathVariable Long id){
        return garageServiceService.getServiceById(id);
    }
    @PostMapping("/addTimeSlot/{serviceId}")
    public GarageServiceModel addTimeSlot(@PathVariable Long serviceId, @RequestBody ServiceTimeSlot timeSlot) {
        return garageServiceService.addTimeSlot(serviceId, timeSlot);
    }

    @DeleteMapping("/deleteTimeSlot/{serviceId}/{timeSlotId}")
    public GarageServiceModel deleteTimeSlot(@PathVariable Long serviceId, @PathVariable Long timeSlotId) {
        return garageServiceService.removeTimeSlot(serviceId, timeSlotId);
    }

    @PutMapping("/updateTimeSlot/{serviceId}/{timeSlotId}/{newAvailability}")
    public GarageServiceModel updateTimeSlotAvailability(
            @PathVariable Long serviceId,
            @PathVariable Long timeSlotId,
            @PathVariable boolean newAvailability) {
        return garageServiceService.updateTimeSlotAvailability(serviceId, timeSlotId, newAvailability);
    }
    @GetMapping("/{serviceId}/availableTimeSlots")
    public List<ServiceTimeSlot> getAvailableTimeSlots(@PathVariable Long serviceId) {
        return garageServiceService.getAvailableTimeSlots(serviceId);
    }


}

