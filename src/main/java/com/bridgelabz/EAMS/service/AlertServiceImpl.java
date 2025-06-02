package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.AlertResponse;
import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.entity.Alert;
import com.bridgelabz.EAMS.entity.AlertStatus;
import com.bridgelabz.EAMS.entity.AlertType;
import com.bridgelabz.EAMS.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertServiceImpl implements IAlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private MailService mailService;

    private final double TEMP_THRESHOLD = 75.0;
    private final double PRESSURE_THRESHOLD = 100.0;

    @Override
    public List<AlertResponse> getActiveAlerts() {
        return alertRepository.findByStatus(AlertStatus.ACTIVE)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertResponse> getResolvedAlerts() {
        return alertRepository.findByStatus(AlertStatus.RESOLVED)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void resolveAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setStatus(AlertStatus.RESOLVED);
        alertRepository.save(alert);
    }

    @Override
    public void checkAndCreateAlert(SensorDataRequest request) {
        if (request.getTemperature() > TEMP_THRESHOLD) {
            createAlert(request.getAssetId(), AlertType.TEMP_HIGH, "Temperature exceeded: " + request.getTemperature());
        }
        if (request.getPressure() > PRESSURE_THRESHOLD) {
            createAlert(request.getAssetId(), AlertType.PRESSURE_HIGH, "Pressure exceeded: " + request.getPressure());
        }
    }

    private void createAlert(Long assetId, AlertType type, String message) {
        Alert alert = new Alert();
        alert.setAssetId(assetId);
        alert.setType(type);
        alert.setMessage(message);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setTriggeredAt(LocalDateTime.now());
        alertRepository.save(alert);

        String emailBody = "Alert Type: " + type +
                "\nAsset ID: " + assetId +
                "\nMessage: " + message +
                "\nTime: " + alert.getTriggeredAt();

        mailService.sendMail("rs7442726@gmail.com", "ALERT: " + type, emailBody);
    }

    private AlertResponse mapToDto(Alert alert) {
        AlertResponse dto = new AlertResponse();
        dto.setId(alert.getId());
        dto.setAssetId(alert.getAssetId());
        dto.setType(alert.getType());
        dto.setMessage(alert.getMessage());
        dto.setStatus(alert.getStatus());
        dto.setTriggeredAt(alert.getTriggeredAt());
        return dto;
    }
}
