package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.AlertResponse;
import com.bridgelabz.EAMS.entity.AlertStatus;
import com.bridgelabz.EAMS.service.IAlertService;
import com.bridgelabz.EAMS.service.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alert Management", description = "Fetch, resolve alerts and send test mail")
public class AlertController {

    @Autowired
    private IAlertService alertService;

    @Autowired
    private MailService mailService;

    @GetMapping("/active")
    public List<AlertResponse> getActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    @GetMapping("/resolved")
    public List<AlertResponse> getResolvedAlerts() {
        return alertService.getResolvedAlerts();
    }

    @PutMapping("/resolve/{id}")
    public String resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return "Alert resolved.";
    }

    @GetMapping("/send-mail")
    public String sendMail() {
        mailService.sendMail("rs7442726@gmail.com", "Test Subject", "Hello! This is a test email from Spring Boot.");
        return "Mail Sent!";
    }
}
