package com.eams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;
    private double temperature;
    private double pressure;
    private LocalDateTime timestamp;

    public SensorData() {}

    public SensorData(String assetName, double temperature, double pressure, LocalDateTime timestamp) {
        this.assetName = assetName;
        this.temperature = temperature;
        this.pressure = pressure;
        this.timestamp = timestamp;
    }

    // Getters and Setters...
}

