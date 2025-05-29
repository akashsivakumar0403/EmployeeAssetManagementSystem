package com.eams.controller;

import com.eams.dto.SnoozeRequest;
import com.eams.entity.Alert;
import com.eams.entity.Threshold;
import com.eams.entity.SnoozedAlertType;
import com.eams.repository.SnoozedAlertTypeRepository;
import com.eams.service.AlertService;
import com.eams.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;
    private final EmailService emailService;
    private final SnoozedAlertTypeRepository snoozedRepo;

    @Autowired
    public AlertController(AlertService alertService,
                           EmailService emailService,
                           SnoozedAlertTypeRepository snoozedRepo) {
        this.alertService = alertService;
        this.emailService = emailService;
        this.snoozedRepo = snoozedRepo;
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

    // Process sensor reading and trigger alert if needed
    @PostMapping("/process")
    public ResponseEntity<String> processSensorReading(
            @RequestParam String sensorName,
            @RequestParam Double sensorValue) {
        alertService.processSensorReading(sensorName, sensorValue);
        return ResponseEntity.ok("Processed sensor reading for " + sensorName);
    }

    // Test email sending endpoint
    @GetMapping("/test-email")
    public String testEmail() {
        emailService.sendSimpleAlertEmail(
                "rs7442726@gmail.com",
                "Test Email Subject",
                "This is a test email body."
        );
        return "Email sent (check inbox/spam)";
    }

    // New endpoint to snooze alerts based on alertType and assetId
    @PostMapping("/snooze")
    public ResponseEntity<String> snoozeAlert(@RequestBody SnoozeRequest request) {
        SnoozedAlertType snoozed = new SnoozedAlertType();
        snoozed.setAlertType(request.getAlertType());
        snoozed.setAssetId(request.getAssetId());
        snoozed.setSnoozedUntil(LocalDateTime.now().plusMinutes(request.getDurationMinutes()));
        snoozedRepo.save(snoozed);

        return ResponseEntity.ok("Snoozed for " + request.getDurationMinutes() + " minutes.");
    }
    @PutMapping("/thresholds/{id}/frequency")
    public ResponseEntity<String> updateThresholdFrequency(
            @PathVariable Long id,
            @RequestParam long frequencyMinutes) {
        alertService.updateAlertFrequency(id, frequencyMinutes);
        return ResponseEntity.ok("Alert frequency updated to " + frequencyMinutes + " minutes for threshold ID " + id);
    }

}
