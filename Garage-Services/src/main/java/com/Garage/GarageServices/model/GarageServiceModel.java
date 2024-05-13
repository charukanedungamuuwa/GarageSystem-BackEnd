package com.Garage.GarageServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor

public class GarageServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long service_id;

    private String category;
    private String service_name;
    private double service_cost;
    private String description;
    private int duration;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_id")
    private List<ServiceTimeSlot> timeSlots = new ArrayList<>();

    public GarageServiceModel() {
        this.service_id = 1L;
    }






    }

