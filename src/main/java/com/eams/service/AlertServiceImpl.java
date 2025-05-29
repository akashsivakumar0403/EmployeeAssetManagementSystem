package com.eams.service;

import com.eams.entity.Alert;
import com.eams.entity.Threshold;
import com.eams.repository.AlertRepository;
import com.eams.repository.ThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertServiceImpl implements IAlertService {

    @Autowired
    private ThresholdRepository thresholdRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Override
    public void processSensorReading(String sensorName, Double sensorValue) {
        // your existing implementation
    }

    @Override
    public List<Alert> getActiveAlerts() {
        // your existing implementation
        return null; // placeholder
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return alertRepository.findById(id);
    }

    @Override
    public Threshold setThreshold(String sensorName, Double lowerLimit, Double upperLimit) {
        // your existing implementation
        return null; // placeholder
    }

    @Override
    public void snoozeAlert(Long thresholdId, long snoozeDurationMinutes) {
        // your existing implementation
    }

    @Override
    public void updateAlertFrequency(Long thresholdId, long frequencyMinutes) {
        Optional<Threshold> thresholdOpt = thresholdRepository.findById(thresholdId);
        thresholdOpt.ifPresent(threshold -> {
        	threshold.setAlertFrequencyMinutes(frequencyMinutes);
        	Long freq = threshold.getAlertFrequencyMinutes();
            thresholdRepository.save(threshold);
        });
    }
}
