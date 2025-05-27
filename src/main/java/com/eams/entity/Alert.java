package com.eams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;
    private String message;
    private String type;  // TEMPERATURE or PRESSURE
    private double value;
    private LocalDateTime timestamp;

    public Alert() {}

    public Alert(String assetName, String message, String type, double value, LocalDateTime timestamp) {
        this.assetName = assetName;
        this.message = message;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters and Setters...
}
