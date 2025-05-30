package com.bridgelabz.EAMS.dto.sensor;

import java.time.LocalDateTime;

public class SensorDataResponse {
    private double temperature;
    private double pressure;
    private LocalDateTime timestamp;
    private Long assetId;

    // Getters and Setters
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Long getAssetId() {
        return assetId;
    }
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}
