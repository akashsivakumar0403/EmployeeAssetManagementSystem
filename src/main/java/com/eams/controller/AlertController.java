package com.eams.controller;

import com.eams.entity.Alert;
import com.eams.entity.Threshold;
import com.eams.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    // Constructor injection (preferred)
    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // Get all active alerts
    @GetMapping("/active")
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        List<Alert> alerts = alertService.getActiveAlerts();
        return ResponseEntity.ok(alerts);
    }

    // Snooze alert by threshold ID
    @PostMapping("/snooze/{thresholdId}")
    public ResponseEntity<Void> snoozeAlert(
            @PathVariable Long thresholdId,
            @RequestParam long durationMinutes) {
        alertService.snoozeAlert(thresholdId, durationMinutes);
        return ResponseEntity.ok().build();
    }

    // Update alert frequency for a threshold
    @PostMapping("/frequency/{thresholdId}")
    public ResponseEntity<Void> updateAlertFrequency(
            @PathVariable Long thresholdId,
            @RequestParam long frequencyMinutes) {
        alertService.updateAlertFrequency(thresholdId, frequencyMinutes);
        return ResponseEntity.ok().build();
    }

    // Create or update sensor threshold
    @PostMapping("/threshold")
    public ResponseEntity<Threshold> setThreshold(
            @RequestParam String sensorName,
            @RequestParam Double lowerLimit,
            @RequestParam Double upperLimit) {
        Threshold threshold = alertService.setThreshold(sensorName, lowerLimit, upperLimit);
        return ResponseEntity.ok(threshold);
    }
}
