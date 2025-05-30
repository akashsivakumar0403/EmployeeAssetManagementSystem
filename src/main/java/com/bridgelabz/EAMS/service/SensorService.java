package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.dto.sensor.SensorDataResponse;

import java.util.List;

public interface SensorService {
    SensorDataResponse saveSensorData(SensorDataRequest request);
    List<SensorDataResponse> getSensorDataByAssetId(Long assetId);
}
