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

    public void sendAlertEmail(String sensorName, double value) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rs7442726@gmail.com");
        message.setSubject("Alert: Sensor Threshold Breached");
        message.setText("Sensor " + sensorName + " reported value: " + value);
        message.setFrom(from);
        mailSender.send(message);
    }
}

