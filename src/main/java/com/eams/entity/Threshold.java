package com.eams.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "thresholds")
public class Threshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorName;

    private Double lowerLimit;
    private Double upperLimit;

    private Boolean snoozed;

    private Long snoozeUntilEpoch;

    // Use only this field for alert frequency (rename as you prefer)
    @Column(name = "alert_frequency_minutes")
    private Long alertFrequencyMinutes;

    public Threshold() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Boolean getSnoozed() {
        return snoozed;
    }

    public void setSnoozed(Boolean snoozed) {
        this.snoozed = snoozed;
    }

    public Long getSnoozeUntilEpoch() {
        return snoozeUntilEpoch;
    }

    public void setSnoozeUntilEpoch(Long snoozeUntilEpoch) {
        this.snoozeUntilEpoch = snoozeUntilEpoch;
    }

    public Long getAlertFrequencyMinutes() {
        return alertFrequencyMinutes;
    }

    public void setAlertFrequencyMinutes(Long alertFrequencyMinutes) {
        this.alertFrequencyMinutes = alertFrequencyMinutes;
    }

    public boolean isSnoozed() {
        return Boolean.TRUE.equals(snoozed);
    }
}
