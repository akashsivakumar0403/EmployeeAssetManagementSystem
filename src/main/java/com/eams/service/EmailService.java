package com.eams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    // Your existing alert email method
    public void sendAlertEmail(String sensorName, double value) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rs7442726@gmail.com");  // fixed recipient for alert emails
        message.setSubject("Alert: Sensor Threshold Breached");
        message.setText("Sensor " + sensorName + " reported value: " + value);
        message.setFrom(from);
        mailSender.send(message);
    }

    // New generic method to send any email (used by test-email endpoint)
    public void sendSimpleAlertEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        mailSender.send(message);
    }
}

