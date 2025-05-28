package com.eams.controller;

import com.eams.dto.SensorDataRequestDTO;
import com.eams.dto.SensorDataResponseDTO;
import com.eams.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataController {
    
    @Autowired
    private SensorDataService sensorDataService;
    
    // Create new sensor data
    @PostMapping
    public ResponseEntity<SensorDataResponseDTO> createSensorData(@RequestBody SensorDataRequestDTO requestDTO) {
        SensorDataResponseDTO responseDTO = sensorDataService.createSensorData(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    // Get sensor data by ID
    @GetMapping("/{id}")
    public ResponseEntity<SensorDataResponseDTO> getSensorDataById(@PathVariable int id) {
        SensorDataResponseDTO responseDTO = sensorDataService.getSensorDataById(id);
        return ResponseEntity.ok(responseDTO);
    }
    
    // Get all sensor data
    @GetMapping
    public ResponseEntity<List<SensorDataResponseDTO>> getAllSensorData() {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getAllSensorData();
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data by asset ID
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataByAssetId(@PathVariable int assetId) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataByAssetId(assetId);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data by sensor type
    @GetMapping("/type/{sensorType}")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataBySensorType(@PathVariable String sensorType) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataBySensorType(sensorType);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data by asset ID and sensor type
    @GetMapping("/asset/{assetId}/type/{sensorType}")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataByAssetIdAndSensorType(
            @PathVariable int assetId, @PathVariable String sensorType) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataByAssetIdAndSensorType(assetId, sensorType);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data with alerts
    @GetMapping("/alerts")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataWithAlerts() {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataWithAlerts();
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data with alerts for specific asset
    @GetMapping("/asset/{assetId}/alerts")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataWithAlertsByAssetId(@PathVariable int assetId) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataWithAlertsByAssetId(assetId);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data within time range
    @GetMapping("/timerange")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataByTimeRange(startTime, endTime);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get sensor data for asset within time range
    @GetMapping("/asset/{assetId}/timerange")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorDataByAssetIdAndTimeRange(
            @PathVariable int assetId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getSensorDataByAssetIdAndTimeRange(assetId, startTime, endTime);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get latest sensor data for each asset
    @GetMapping("/latest")
    public ResponseEntity<List<SensorDataResponseDTO>> getLatestSensorDataForEachAsset() {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getLatestSensorDataForEachAsset();
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get latest sensor data for specific asset
    @GetMapping("/asset/{assetId}/latest")
    public ResponseEntity<List<SensorDataResponseDTO>> getLatestSensorDataForAsset(@PathVariable int assetId) {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getLatestSensorDataForAsset(assetId);
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Get recent sensor data (last 24 hours)
    @GetMapping("/recent")
    public ResponseEntity<List<SensorDataResponseDTO>> getRecentSensorData() {
        List<SensorDataResponseDTO> sensorDataList = sensorDataService.getRecentSensorData();
        return ResponseEntity.ok(sensorDataList);
    }
    
    // Update sensor data
    @PutMapping("/{id}")
    public ResponseEntity<SensorDataResponseDTO> updateSensorData(@PathVariable int id, 
                                                                 @RequestBody SensorDataRequestDTO requestDTO) {
        SensorDataResponseDTO responseDTO = sensorDataService.updateSensorData(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    
    // Delete sensor data
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSensorData(@PathVariable int id) {
        sensorDataService.deleteSensorData(id);
        return ResponseEntity.ok("Sensor data deleted successfully");
    }
}
