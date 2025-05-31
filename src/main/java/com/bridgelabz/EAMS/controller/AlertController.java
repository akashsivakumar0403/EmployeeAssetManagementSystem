package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.entity.Alert;
import com.bridgelabz.EAMS.entity.AlertStatus;
import com.bridgelabz.EAMS.repository.AlertRepository;
import com.bridgelabz.EAMS.service.MailService;  // Import MailService
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alert Management", description = "Fetch, resolve alerts and send test mail")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private MailService mailService;  // Inject MailService

    @GetMapping("/active")
    public List<Alert> getActiveAlerts() {
        return alertRepository.findByStatus(AlertStatus.ACTIVE);
    }

    @GetMapping("/resolved")
    public List<Alert> getResolvedAlerts() {
        return alertRepository.findByStatus(AlertStatus.RESOLVED);
    }

    @PutMapping("/resolve/{id}")
    public String resolveAlert(@PathVariable Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setStatus(AlertStatus.RESOLVED);
        alertRepository.save(alert);
        return "Alert resolved.";
    }
    @GetMapping("/send-mail")
    public String sendMail() {
        mailService.sendMail("rs7442726@gmail.com", "Test Subject", "Hello! This is a test email from Spring Boot.");
        return "Mail Sent!";
    }
}
