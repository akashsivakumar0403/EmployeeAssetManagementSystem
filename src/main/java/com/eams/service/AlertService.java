package com.eams.service;

import com.eams.entity.Alert;
import com.eams.entity.AlertSeverity;
import com.eams.entity.Employee;
import com.eams.entity.Threshold;
import com.eams.repository.AlertRepository;
import com.eams.repository.EmployeeRepository;
import com.eams.repository.SnoozedAlertTypeRepository;
import com.eams.repository.ThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Autowired
    private ThresholdRepository thresholdRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SnoozedAlertTypeRepository snoozedAlertTypeRepository;

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
            Long frequency = threshold.getAlertFrequencyMinutes();
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

    // Create alert and send email notification to all managers, with snooze and severity checks
    public void handleAlert(Alert alert) {
        alertRepository.save(alert);

        // Skip email if alert is non-critical and snoozed
        if (alert.getSeverity() != AlertSeverity.CRITICAL && isAlertSnoozed(alert)) {
            System.out.println("Alert snoozed: " + alert.getAlertType());
            return;
        }

        sendAlertEmailToManagers(alert);
    }

    private void triggerAlert(String sensorName, Double sensorValue, String alertType, String message) {
        Alert alert = new Alert();
        alert.setSensorName(sensorName);
        alert.setSensorValue(sensorValue);
        alert.setStatus(STATUS_ACTIVE);
        alert.setMessage(message);
        alert.setCreatedAt(LocalDateTime.now());
        alert.setAlertType(alertType);
        alert.setSeverity(AlertSeverity.WARNING);  // example default severity, adjust as needed

        handleAlert(alert);
    }

    // Send email to all managers with role "MANAGER"
    private void sendAlertEmailToManagers(Alert alert) {
        List<Employee> managers = employeeRepository.findByRole("MANAGER");

        List<String> emailList = managers.stream()
                                         .map(Employee::getEmail)
                                         .collect(Collectors.toList());

        if (emailList.isEmpty()) {
            System.out.println("No managers found to send alert");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rs7442726@gmail.com");
        message.setTo(emailList.toArray(new String[0]));
        message.setSubject("Alert Triggered: " + alert.getAlertType());
        message.setText("An alert has been triggered:\n\n" +
                        "Type: " + alert.getAlertType() + "\n" +
                        "Message: " + alert.getMessage() + "\n" +
                        "Sensor: " + alert.getSensorName() + "\n" +
                        "Value: " + alert.getSensorValue() + "\n" +
                        "Time: " + alert.getCreatedAt());

        mailSender.send(message);
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
            threshold.setAlertFrequencyMinutes(frequencyMinutes);  // ✅ correct method name
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
            threshold.setAlertFrequencyMinutes(0L);  
        }
        return thresholdRepository.save(threshold);
    }

   

    // Get all currently active alerts
    public List<Alert> getActiveAlerts() {
        return alertRepository.findAll().stream()
                .filter(alert -> STATUS_ACTIVE.equals(alert.getStatus()))
                .toList();
    }

    public boolean isAlertSnoozed(Alert alert) {
        return snoozedAlertTypeRepository.findByAlertTypeAndAssetId(alert.getAlertType(), alert.getAssetId())
                .filter(snoozed -> snoozed.getSnoozedUntil().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
