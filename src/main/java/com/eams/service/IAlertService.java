package com.eams.service;

import com.eams.entity.Alert;
import com.eams.entity.Threshold;

import java.util.List;
import java.util.Optional;

public interface IAlertService {
    void processSensorReading(String sensorName, Double sensorValue);
    List<Alert> getActiveAlerts();
    Optional<Alert> findById(Long id);
    Threshold setThreshold(String sensorName, Double lowerLimit, Double upperLimit);
    void snoozeAlert(Long thresholdId, long snoozeDurationMinutes);

    // Method to update alert frequency
    void updateAlertFrequency(Long thresholdId, long frequencyMinutes);
}
