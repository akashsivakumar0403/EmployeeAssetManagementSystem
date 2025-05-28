package com.eams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "sensor_data")
public class SensorData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @Column(nullable = false)
    private String sensorType; // TEMPERATURE, PRESSURE, VIBRATION, etc.
    
    @Column(nullable = false)
    private double value;
    
    @Column(nullable = false)
    private String unit; // °C, PSI, Hz, etc.
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private double thresholdMin;
    
    @Column(nullable = false)
    private double thresholdMax;
    
    @Column(nullable = false)
    private boolean alertTriggered;
    
    // Constructors
    public SensorData() {
        this.timestamp = LocalDateTime.now();
        this.alertTriggered = false;
    }
    
    public SensorData(Asset asset, String sensorType, double value, String unit, 
                     double thresholdMin, double thresholdMax) {
        this();
        this.asset = asset;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.alertTriggered = checkThresholdViolation();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    
    public double getValue() { return value; }
    public void setValue(double value) { 
        this.value = value;
        this.alertTriggered = checkThresholdViolation();
    }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public double getThresholdMin() { return thresholdMin; }
    public void setThresholdMin(double thresholdMin) { 
        this.thresholdMin = thresholdMin;
        this.alertTriggered = checkThresholdViolation();
    }
    
    public double getThresholdMax() { return thresholdMax; }
    public void setThresholdMax(double thresholdMax) { 
        this.thresholdMax = thresholdMax;
        this.alertTriggered = checkThresholdViolation();
    }
    
    public boolean isAlertTriggered() { return alertTriggered; }
    public void setAlertTriggered(boolean alertTriggered) { this.alertTriggered = alertTriggered; }
    
    // Helper method to check threshold violation
    private boolean checkThresholdViolation() {
        return value < thresholdMin || value > thresholdMax;
    }
    
    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", sensorType='" + sensorType + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", timestamp=" + timestamp +
                ", thresholdMin=" + thresholdMin +
                ", thresholdMax=" + thresholdMax +
                ", alertTriggered=" + alertTriggered +
                '}';
    }
}

