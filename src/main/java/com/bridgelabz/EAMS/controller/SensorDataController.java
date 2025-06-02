package com.bridgelabz.EAMS.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.dto.sensor.SensorDataResponse;
import com.bridgelabz.EAMS.service.ISensorService;

@RestController
@RequestMapping("/api/sensors")
public class SensorDataController {

    @Autowired
    private ISensorService sensorService;

    @PostMapping("/send-data")
    public ResponseEntity<SensorDataResponse> sendSensorData(@RequestBody SensorDataRequest request) {
        SensorDataResponse saved = sensorService.saveSensorData(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<List<SensorDataResponse>> getDataByAsset(@PathVariable("id") Long assetId) {
        return ResponseEntity.ok(sensorService.getSensorDataByAssetId(assetId));
    }
}
