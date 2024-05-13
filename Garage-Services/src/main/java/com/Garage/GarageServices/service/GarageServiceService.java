package com.Garage.GarageServices.service;

import com.Garage.GarageServices.model.GarageServiceModel;
import com.Garage.GarageServices.model.ServiceTimeSlot;
import com.Garage.GarageServices.repository.GarageServiceRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GarageServiceService {

    @Autowired
    private GarageServiceRepository garageServiceRepository;





    public List<ServiceTimeSlot> getAvailableTimeSlots(Long serviceId) {
        GarageServiceModel service = garageServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + serviceId));

        List<ServiceTimeSlot> timeSlots = service.getTimeSlots();

        // Filter available time slots
        List<ServiceTimeSlot> availableTimeSlots = timeSlots.stream()
                .filter(ServiceTimeSlot::isAvailable)
                .collect(Collectors.toList());

        return availableTimeSlots;
    }











    public List<GarageServiceModel> getAllServices() {
        return garageServiceRepository.findAll();
    }

    public GarageServiceModel addService(GarageServiceModel service) {
        return garageServiceRepository.save(service);
    }

    public GarageServiceModel editService(Long id, GarageServiceModel newService) {
        GarageServiceModel existingService = garageServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + id));

        existingService.setCategory(newService.getCategory());
        existingService.setService_name(newService.getService_name());
        existingService.setService_cost(newService.getService_cost());
        existingService.setDescription(newService.getDescription());
        existingService.setDuration(newService.getDuration());

        return garageServiceRepository.save(existingService);
    }

    public String deleteService(Long id) {
        garageServiceRepository.deleteById(id);
        return "Deleted Succesfully";
    }

    public Optional<GarageServiceModel> getServiceById(Long id){
        return garageServiceRepository.findById(id);
    }

    public GarageServiceModel addTimeSlot(Long serviceId, ServiceTimeSlot timeSlot) {
        GarageServiceModel service = garageServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + serviceId));

        List<ServiceTimeSlot> timeSlots = service.getTimeSlots();
        timeSlots.add(timeSlot);

        return garageServiceRepository.save(service);
    }

    public GarageServiceModel removeTimeSlot(Long serviceId, Long timeSlotId) {
        GarageServiceModel service = garageServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + serviceId));

        List<ServiceTimeSlot> timeSlots = service.getTimeSlots();
        timeSlots.removeIf(timeSlot -> timeSlot.getId().equals(timeSlotId));

        return garageServiceRepository.save(service);
    }

    public GarageServiceModel updateTimeSlotAvailability(Long serviceId, Long timeSlotId, boolean newAvailability) {
        GarageServiceModel service = garageServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id: " + serviceId));

        List<ServiceTimeSlot> timeSlots = service.getTimeSlots();
        for (ServiceTimeSlot timeSlot : timeSlots) {
            if (timeSlot.getId().equals(timeSlotId)) {
                timeSlot.setAvailable(newAvailability);
                break;
            }
        }

        return garageServiceRepository.save(service);
    }
    public void saveGarageService(GarageServiceModel garageServiceModel) {
        garageServiceRepository.save(garageServiceModel);
    }

}
