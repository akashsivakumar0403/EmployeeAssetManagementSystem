package com.eams.dto;

public class SensorDataRequestDTO {
    private int assetId;
    private String sensorType;
    private double value;
    private String unit;
    private double thresholdMin;
    private double thresholdMax;
    
    // Constructors
    public SensorDataRequestDTO() {}
    
    public SensorDataRequestDTO(int assetId, String sensorType, double value, 
                               String unit, double thresholdMin, double thresholdMax) {
        this.assetId = assetId;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
    }
    
    // Getters and Setters
    public int getAssetId() { return assetId; }
    public void setAssetId(int assetId) { this.assetId = assetId; }
    
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public double getThresholdMin() { return thresholdMin; }
    public void setThresholdMin(double thresholdMin) { this.thresholdMin = thresholdMin; }
    
    public double getThresholdMax() { return thresholdMax; }
    public void setThresholdMax(double thresholdMax) { this.thresholdMax = thresholdMax; }
}
