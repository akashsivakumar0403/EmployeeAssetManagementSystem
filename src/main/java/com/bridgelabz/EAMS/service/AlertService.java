package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.AlertResponse;
import com.bridgelabz.EAMS.dto.SensorDataRequest;

import java.util.List;

public interface AlertService {
    List<AlertResponse> getActiveAlerts();
    List<AlertResponse> getResolvedAlerts();
    void resolveAlert(Long alertId);
	void checkAndCreateAlert(SensorDataRequest request);
}
