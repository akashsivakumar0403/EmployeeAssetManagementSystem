package com.eams.dto;

import java.time.LocalDateTime;

public class SensorDataResponseDTO {
    private int id;
    private int assetId;
    private String assetName;
    private String sensorType;
    private double value;
    private String unit;
    private LocalDateTime timestamp;
    private double thresholdMin;
    private double thresholdMax;
    private boolean alertTriggered;
    
    // Constructors
    public SensorDataResponseDTO() {}
    
    public SensorDataResponseDTO(int id, int assetId, String assetName, String sensorType, 
                                double value, String unit, LocalDateTime timestamp, 
                                double thresholdMin, double thresholdMax, boolean alertTriggered) {
        this.id = id;
        this.assetId = assetId;
        this.assetName = assetName;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.alertTriggered = alertTriggered;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getAssetId() { return assetId; }
    public void setAssetId(int assetId) { this.assetId = assetId; }
    
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public double getThresholdMin() { return thresholdMin; }
    public void setThresholdMin(double thresholdMin) { this.thresholdMin = thresholdMin; }
    
    public double getThresholdMax() { return thresholdMax; }
    public void setThresholdMax(double thresholdMax) { this.thresholdMax = thresholdMax; }
    
    public boolean isAlertTriggered() { return alertTriggered; }
    public void setAlertTriggered(boolean alertTriggered) { this.alertTriggered = alertTriggered; }
}
