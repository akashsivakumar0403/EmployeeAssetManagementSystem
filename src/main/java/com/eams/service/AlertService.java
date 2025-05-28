package com.eams.service;

import com.eams.entity.Alert;
import com.eams.entity.Threshold;
import com.eams.repository.AlertRepository;
import com.eams.repository.ThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    @Autowired
    private ThresholdRepository thresholdRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_RESOLVED = "RESOLVED";

    // Process sensor reading and trigger alert if thresholds exceeded
    public void processSensorReading(String sensorName, Double sensorValue) {
        Optional<Threshold> thresholdOpt = thresholdRepository.findBySensorName(sensorName);
        if (thresholdOpt.isEmpty()) {
            // No threshold defined for this sensor
            return;
        }
        Threshold threshold = thresholdOpt.get();

        // Check if alert is snoozed and still valid
        if (threshold.isSnoozed()) {
            long nowEpoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            Long snoozeUntil = threshold.getSnoozeUntilEpoch();
            if (snoozeUntil != null && nowEpoch < snoozeUntil) {
                // Snoozed period not expired, skip alert
                return;
            } else {
                // Snooze expired - reset
                threshold.setSnoozed(false);
                threshold.setSnoozeUntilEpoch(null);
                thresholdRepository.save(threshold);
            }
        }

        // Check alert frequency to avoid flooding alerts
        List<Alert> activeAlerts = alertRepository.findBySensorNameAndStatus(sensorName, STATUS_ACTIVE);
        if (!activeAlerts.isEmpty()) {
            Alert recentAlert = activeAlerts.get(0);
            LocalDateTime lastAlertTime = recentAlert.getCreatedAt();
            Long frequency = threshold.getFrequencyInMinutes();
            if (frequency == null) frequency = 0L;  // default 0 if not set
            if (lastAlertTime.plusMinutes(frequency).isAfter(LocalDateTime.now())) {
                // Frequency threshold not met, skip alert
                return;
            }
        }

        // Check sensor value against thresholds and trigger alert if needed
        if (sensorValue > threshold.getUpperLimit()) {
            triggerAlert(sensorName, sensorValue, "UPPER_LIMIT",
                    "Sensor reading exceeded upper limit: " + threshold.getUpperLimit());
        } else if (sensorValue < threshold.getLowerLimit()) {
            triggerAlert(sensorName, sensorValue, "LOWER_LIMIT",
                    "Sensor reading fell below lower limit: " + threshold.getLowerLimit());
        } else {
            // If sensor value normal and an alert exists, resolve it
            if (!activeAlerts.isEmpty()) {
                Alert alertToResolve = activeAlerts.get(0);
                alertToResolve.setStatus(STATUS_RESOLVED);
                alertToResolve.setResolvedAt(LocalDateTime.now());
                alertRepository.save(alertToResolve);
            }
        }
    }

    // Create alert and send email notification
    private void triggerAlert(String sensorName, Double sensorValue, String alertType, String message) {
        Alert alert = new Alert();
        alert.setSensorName(sensorName);
        alert.setSensorValue(sensorValue);
        alert.setStatus(STATUS_ACTIVE);
        alert.setMessage(message);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAlertType(alertType);

        alertRepository.save(alert);

        sendEmailAlert(sensorName, message);
    }

    private void sendEmailAlert(String sensorName, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("technician@example.com"); // Replace with real recipients or config
        mailMessage.setSubject("Alert: Sensor " + sensorName);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    // Allow user to snooze alerts
    public void snoozeAlert(Long thresholdId, long snoozeDurationMinutes) {
        Optional<Threshold> thresholdOpt = thresholdRepository.findById(thresholdId);
        thresholdOpt.ifPresent(threshold -> {
            threshold.setSnoozed(true);
            long snoozeUntil = LocalDateTime.now().plusMinutes(snoozeDurationMinutes).toEpochSecond(ZoneOffset.UTC);
            threshold.setSnoozeUntilEpoch(snoozeUntil);
            thresholdRepository.save(threshold);
        });
    }

    // Admin can update alert frequency per threshold
    public void updateAlertFrequency(Long thresholdId, long frequencyMinutes) {
        Optional<Threshold> thresholdOpt = thresholdRepository.findById(thresholdId);
        thresholdOpt.ifPresent(threshold -> {
            threshold.setFrequencyInMinutes(frequencyMinutes);
            thresholdRepository.save(threshold);
        });
    }

    // Admin can create or update sensor threshold
    public Threshold setThreshold(String sensorName, Double lowerLimit, Double upperLimit) {
        Optional<Threshold> thresholdOpt = thresholdRepository.findBySensorName(sensorName);
        Threshold threshold;
        if (thresholdOpt.isPresent()) {
            threshold = thresholdOpt.get();
            threshold.setLowerLimit(lowerLimit);
            threshold.setUpperLimit(upperLimit);
        } else {
            threshold = new Threshold();
            threshold.setSensorName(sensorName);
            threshold.setLowerLimit(lowerLimit);
            threshold.setUpperLimit(upperLimit);
            threshold.setSnoozed(false);
            threshold.setFrequencyInMinutes(0L);
        }
        return thresholdRepository.save(threshold);
    }

    // Get all currently active alerts
    public List<Alert> getActiveAlerts() {
        return alertRepository.findAll().stream()
                .filter(alert -> STATUS_ACTIVE.equals(alert.getStatus()))
                .toList();
    }
}