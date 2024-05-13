package com.Garage.GarageServices.kafka;

import com.Garage.GarageServices.model.GarageServiceModel;
import com.Garage.GarageServices.model.ServiceTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerService {
    private static final String TOPIC_NAME = "garage-service-topic";

    @Autowired
    private KafkaTemplate<String, GarageServiceModel> kafkaTemplate;

    public void sendGarageServiceToKafka(GarageServiceModel garageService) {

        kafkaTemplate.send(TOPIC_NAME, garageService);
        System.out.println("Sent message to Kafka: " + garageService);
    }

    public void produceGarageServiceModels() {
        GarageServiceModel oilChangeService = createOilChangeService();
        GarageServiceModel brakePadReplacementService = createBrakePadReplacementService();
        GarageServiceModel tireRotationService = createTireRotationService();
        GarageServiceModel carWashService = createCarWashService();
        GarageServiceModel engineDiagnosticService = createEngineDiagnosticService();
        GarageServiceModel headlightReplacementService = createHeadlightReplacementService();
        GarageServiceModel wheelAlignmentService = createWheelAlignmentService();
        GarageServiceModel batteryReplacementService = createBatteryReplacementService();
        GarageServiceModel coolantFlushService = createCoolantFlushService();
        GarageServiceModel interiorDetailingService = createInteriorDetailingService();

        // Produce messages to Kafka
        sendGarageServiceToKafka(oilChangeService);
        sendGarageServiceToKafka(brakePadReplacementService);
        sendGarageServiceToKafka(tireRotationService);
        sendGarageServiceToKafka(carWashService);
        sendGarageServiceToKafka(engineDiagnosticService);
        sendGarageServiceToKafka(headlightReplacementService);
        sendGarageServiceToKafka(wheelAlignmentService);
        sendGarageServiceToKafka(batteryReplacementService);
        sendGarageServiceToKafka(coolantFlushService);
        sendGarageServiceToKafka(interiorDetailingService);
    }

    private GarageServiceModel createOilChangeService() {
        GarageServiceModel oilChangeService = new GarageServiceModel();
        oilChangeService.setService_id(1L);
        oilChangeService.setCategory("Car Repair");
        oilChangeService.setService_name("Oil Change");
        oilChangeService.setService_cost(50.0);
        oilChangeService.setDescription("Standard oil change service");
        oilChangeService.setDuration(30);
        oilChangeService.setTimeSlots(createOilChangeTimeSlots());
        return oilChangeService;
    }

    private List<ServiceTimeSlot> createOilChangeTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"08:00 AM", "08:30 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"09:00 AM", "09:30 AM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createBrakePadReplacementService() {
        GarageServiceModel brakePadReplacementService = new GarageServiceModel();
        brakePadReplacementService.setService_id(2L);
        brakePadReplacementService.setCategory("Car Repair");
        brakePadReplacementService.setService_name("Brake Pad Replacement");
        brakePadReplacementService.setService_cost(120.0);
        brakePadReplacementService.setDescription("Replace brake pads on all wheels");
        brakePadReplacementService.setDuration(60);
        brakePadReplacementService.setTimeSlots(createBrakePadReplacementTimeSlots());
        return brakePadReplacementService;
    }

    private List<ServiceTimeSlot> createBrakePadReplacementTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createTireRotationService() {
        GarageServiceModel tireRotationService = new GarageServiceModel();
        tireRotationService.setService_id(3L);
        tireRotationService.setCategory("Car Repair");
        tireRotationService.setService_name("Tire Rotation");
        tireRotationService.setService_cost(60.0);
        tireRotationService.setDescription("Rotate tires for even wear");
        tireRotationService.setDuration(45);
        tireRotationService.setTimeSlots(createTireRotationTimeSlots());
        return tireRotationService;
    }

    private List<ServiceTimeSlot> createTireRotationTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"09:30 AM", "10:15 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"11:30 AM", "12:15 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createCarWashService() {
        GarageServiceModel carWashService = new GarageServiceModel();
        carWashService.setService_id(4L);
        carWashService.setCategory("Car Wash");
        carWashService.setService_name("Exterior Car Wash");
        carWashService.setService_cost(25.0);
        carWashService.setDescription("Wash and dry the exterior of the car");
        carWashService.setDuration(20);
        carWashService.setTimeSlots(createCarWashTimeSlots());
        return carWashService;
    }

    private List<ServiceTimeSlot> createCarWashTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"10:00 AM", "10:20 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"12:30 PM", "12:50 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createEngineDiagnosticService() {
        GarageServiceModel engineDiagnosticService = new GarageServiceModel();
        engineDiagnosticService.setService_id(5L);
        engineDiagnosticService.setCategory("Car Repair");
        engineDiagnosticService.setService_name("Engine Diagnostic");
        engineDiagnosticService.setService_cost(80.0);
        engineDiagnosticService.setDescription("Check engine for issues and diagnostics");
        engineDiagnosticService.setDuration(60);
        engineDiagnosticService.setTimeSlots(createEngineDiagnosticTimeSlots());
        return engineDiagnosticService;
    }

    private List<ServiceTimeSlot> createEngineDiagnosticTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"11:00 AM", "12:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createHeadlightReplacementService() {
        GarageServiceModel headlightReplacementService = new GarageServiceModel();
        headlightReplacementService.setService_id(6L);
        headlightReplacementService.setCategory("Car Repair");
        headlightReplacementService.setService_name("Headlight Replacement");
        headlightReplacementService.setService_cost(40.0);
        headlightReplacementService.setDescription("Replace headlight bulbs");
        headlightReplacementService.setDuration(30);
        headlightReplacementService.setTimeSlots(createHeadlightReplacementTimeSlots());
        return headlightReplacementService;
    }

    private List<ServiceTimeSlot> createHeadlightReplacementTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"01:00 PM", "01:30 PM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"03:30 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createWheelAlignmentService() {
        GarageServiceModel wheelAlignmentService = new GarageServiceModel();
        wheelAlignmentService.setService_id(7L);
        wheelAlignmentService.setCategory("Car Repair");
        wheelAlignmentService.setService_name("Wheel Alignment");
        wheelAlignmentService.setService_cost(70.0);
        wheelAlignmentService.setDescription("Align wheels for optimal driving");
        wheelAlignmentService.setDuration(60);
        wheelAlignmentService.setTimeSlots(createWheelAlignmentTimeSlots());
        return wheelAlignmentService;
    }

    private List<ServiceTimeSlot> createWheelAlignmentTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"10:30 AM", "11:30 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"01:30 PM", "02:30 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createBatteryReplacementService() {
        GarageServiceModel batteryReplacementService = new GarageServiceModel();
        batteryReplacementService.setService_id(8L);
        batteryReplacementService.setCategory("Car Repair");
        batteryReplacementService.setService_name("Battery Replacement");
        batteryReplacementService.setService_cost(100.0);
        batteryReplacementService.setDescription("Replace car battery with new one");
        batteryReplacementService.setDuration(45);
        batteryReplacementService.setTimeSlots(createBatteryReplacementTimeSlots());
        return batteryReplacementService;
    }

    private List<ServiceTimeSlot> createBatteryReplacementTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"09:00 AM", "09:45 AM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"02:00 PM", "02:45 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createCoolantFlushService() {
        GarageServiceModel coolantFlushService = new GarageServiceModel();
        coolantFlushService.setService_id(9L);
        coolantFlushService.setCategory("Car Repair");
        coolantFlushService.setService_name("Coolant Flush");
        coolantFlushService.setService_cost(60.0);
        coolantFlushService.setDescription("Flush and refill coolant system");
        coolantFlushService.setDuration(60);
        coolantFlushService.setTimeSlots(createCoolantFlushTimeSlots());
        return coolantFlushService;
    }

    private List<ServiceTimeSlot> createCoolantFlushTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"11:30 AM", "12:30 PM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"03:30 PM", "04:30 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    private GarageServiceModel createInteriorDetailingService() {
        GarageServiceModel interiorDetailingService = new GarageServiceModel();
        interiorDetailingService.setService_id(10L);
        interiorDetailingService.setCategory("Car Wash");
        interiorDetailingService.setService_name("Interior Detailing");
        interiorDetailingService.setService_cost(50.0);
        interiorDetailingService.setDescription("Thorough cleaning of car interior");
        interiorDetailingService.setDuration(45);
        interiorDetailingService.setTimeSlots(createInteriorDetailingTimeSlots());
        return interiorDetailingService;
    }

    private List<ServiceTimeSlot> createInteriorDetailingTimeSlots() {
        List<ServiceTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new ServiceTimeSlot(1L,"12:00 PM", "12:45 PM", true));
        timeSlots.add(new ServiceTimeSlot(2L,"04:00 PM", "04:45 PM", true));
        timeSlots.add(new ServiceTimeSlot(3L,"10:00 AM", "11:00 AM", true));
        timeSlots.add(new ServiceTimeSlot(4L,"02:00 PM", "03:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(5L,"03:00 PM", "04:00 PM", true));
        timeSlots.add(new ServiceTimeSlot(6L,"04:00 PM", "05:00 PM", true));
        return timeSlots;
    }

    }
