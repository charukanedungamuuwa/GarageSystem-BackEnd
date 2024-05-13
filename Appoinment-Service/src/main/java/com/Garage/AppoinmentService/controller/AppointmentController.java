package com.Garage.AppoinmentService.controller;

import com.Garage.AppoinmentService.model.Appointment;
import com.Garage.AppoinmentService.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
//@CrossOrigin
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/add")
    public Appointment addAppointment(@RequestBody AddAppointmentRequest request) {
            Long ownerId = request.getOwnerId();
            Long serviceId = request.getServiceId();
            Long vehicleId = request.getVehicleId();

            Appointment appointment=request.getAppointment();
            appointment.setVehicleId(vehicleId);
            appointment.setOwnerId(ownerId);
            appointment.setServiceId(serviceId);
        return appointmentService.addAppointment(appointment);
    }


    @PutMapping("/update/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        return appointmentService.deleteAppointment(id);
    }
    @GetMapping("/get/{ownerId}")
    public List<Appointment> getAppointmentByOwnerId(@PathVariable Long ownerId) {
        return appointmentService.getAppointmentsByOwnerId(ownerId);
    }

    @GetMapping("/hasAppointments/{vehicleId}")
    public boolean hasAppointmentsForVehicle(@PathVariable Long vehicleId) {
        return appointmentService.hasAppointmentsForVehicle(vehicleId);
    }
//
    @GetMapping("/getById/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(appointment);
    }
    //
    public static class AddAppointmentRequest {
        private Long ownerId;
        private Long serviceId;
        private Long vehicleId;
        private Appointment appointment;


        public Long getOwnerId() {
            return ownerId;
        }
        public Long getServiceId() {
            return serviceId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }
        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public Appointment getAppointment() {
            return appointment;
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }
        public Long getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Long vehicleId) {
            this.vehicleId = vehicleId;
        }
    }
}
