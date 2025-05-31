package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.entity.Alert;
import com.bridgelabz.EAMS.entity.AlertStatus;
import com.bridgelabz.EAMS.entity.AlertType;
import com.bridgelabz.EAMS.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlertServiceImplTest {

    @InjectMocks
    private AlertServiceImpl alertService;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetActiveAlerts() {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setAssetId(100L);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setType(AlertType.TEMP_HIGH);
        alert.setTriggeredAt(LocalDateTime.now());

        when(alertRepository.findByStatus(AlertStatus.ACTIVE)).thenReturn(List.of(alert));

        var result = alertService.getActiveAlerts();

        assertEquals(1, result.size());
        assertEquals(AlertStatus.ACTIVE, result.get(0).getStatus());
    }

    @Test
    void testGetResolvedAlerts() {
        Alert alert = new Alert();
        alert.setId(2L);
        alert.setAssetId(200L);
        alert.setStatus(AlertStatus.RESOLVED);
        alert.setType(AlertType.PRESSURE_HIGH);
        alert.setTriggeredAt(LocalDateTime.now());

        when(alertRepository.findByStatus(AlertStatus.RESOLVED)).thenReturn(List.of(alert));

        var result = alertService.getResolvedAlerts();

        assertEquals(1, result.size());
        assertEquals(AlertStatus.RESOLVED, result.get(0).getStatus());
    }

    @Test
    void testResolveAlert() {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setStatus(AlertStatus.ACTIVE);

        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));

        alertService.resolveAlert(1L);

        assertEquals(AlertStatus.RESOLVED, alert.getStatus());
        verify(alertRepository, times(1)).save(alert);
    }

    @Test
    void testCheckAndCreateAlert_createsTemperatureAndPressureAlerts() {
        SensorDataRequest request = new SensorDataRequest();
        request.setAssetId(1L);
        request.setTemperature(80.0);  // exceeds threshold
        request.setPressure(150.0);    // exceeds threshold

        alertService.checkAndCreateAlert(request);

        verify(alertRepository, times(2)).save(any(Alert.class));
        verify(mailService, times(2)).sendMail(anyString(), anyString(), anyString());
    }

    @Test
    void testCheckAndCreateAlert_doesNotTrigger() {
        SensorDataRequest request = new SensorDataRequest();
        request.setAssetId(1L);
        request.setTemperature(50.0);  // normal
        request.setPressure(80.0);     // normal

        alertService.checkAndCreateAlert(request);

        verify(alertRepository, never()).save(any());
        verify(mailService, never()).sendMail(any(), any(), any());
    }
}
