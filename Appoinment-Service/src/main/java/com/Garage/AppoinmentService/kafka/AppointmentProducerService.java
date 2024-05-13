package com.Garage.AppoinmentService.kafka;

import com.Garage.AppoinmentService.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducerService {
    private static final String TOPIC_NAME = "appointment-topic";

    @Autowired
    private KafkaTemplate<String, Appointment> kafkaTemplate;

    public void sendAppointmentToKafka(Appointment appointment) {
        kafkaTemplate.send(TOPIC_NAME, appointment);
        System.out.println("Sent appointment to Kafka: " + appointment);
    }
    public void deleteAppointmentFromKafka(Appointment appointment) {

        Appointment deletionAppointment = new Appointment();
        deletionAppointment.setAppointment_id(appointment.getAppointment_id());
        deletionAppointment.setStatus("DELETED");

        kafkaTemplate.send(TOPIC_NAME, deletionAppointment);
        System.out.println("Sent deletion request for appointment to Kafka: " + appointment);
    }
}
