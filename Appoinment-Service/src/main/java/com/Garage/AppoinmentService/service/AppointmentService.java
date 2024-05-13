package com.Garage.AppoinmentService.service;

import com.Garage.AppoinmentService.kafka.AppointmentProducerService;
import com.Garage.AppoinmentService.model.Appointment;
import com.Garage.AppoinmentService.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentProducerService appointmentProducerService;
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment addAppointment(Appointment appointment) {
//        return appointmentRepository.save(appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        appointmentProducerService.sendAppointmentToKafka(savedAppointment);
        return savedAppointment;
    }
    public String deleteAppointment(Long id) {
        Appointment appointmentToDelete = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));


        appointmentProducerService.deleteAppointmentFromKafka(appointmentToDelete);
        appointmentRepository.deleteById(id);
        return "Deleted Successfully";





    }
    public List<Appointment> getAppointmentsByOwnerId(Long ownerId) {
        return appointmentRepository.findByOwnerId(ownerId);
    }

    public Appointment updateAppointment(Long id, Appointment newappointment) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));


        existingAppointment.setAppointment_date(newappointment.getAppointment_date());
        existingAppointment.setAppointment_time(newappointment.getAppointment_time());
        existingAppointment.setStatus(newappointment.getStatus());
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        appointmentProducerService.sendAppointmentToKafka(updatedAppointment); // Send updated appointment to Kafka
        return updatedAppointment;

    }
    public boolean hasAppointmentsForVehicle(Long vehicleId) {
        return appointmentRepository.hasAppointmentsForVehicle(vehicleId);
    }
    private LocalDateTime convertDateTimeStringToDateTime(String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeString = date + " " + time;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElse(null);
    }
}
