package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.dto.AlertResponse;

import java.util.List;

public interface AlertService {
    void checkAndCreateAlert(SensorDataRequest request); // called from sensor ingestion
    List<AlertResponse> getAllAlerts();
    List<AlertResponse> getActiveAlerts();
    void resolveAlert(Long alertId);
}
