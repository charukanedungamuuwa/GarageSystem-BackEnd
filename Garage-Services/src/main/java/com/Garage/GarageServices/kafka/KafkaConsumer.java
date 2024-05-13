package com.Garage.GarageServices.kafka;

import com.Garage.GarageServices.model.GarageServiceModel;
import com.Garage.GarageServices.service.GarageServiceService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private GarageServiceService garageServiceService;
    @KafkaListener(topics = "garage-service-topic", groupId = "mygroup")
    public void consume(ConsumerRecord<String, GarageServiceModel> record){
        LOGGER.info(String.format("Received message: %s", record.value()));
        garageServiceService.saveGarageService(record.value());
    }

}
