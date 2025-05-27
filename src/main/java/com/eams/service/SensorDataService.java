package com.eams.service;

import com.eams.dto.SensorDataRequestDTO;
import com.eams.dto.SensorDataResponseDTO;
import com.eams.entity.Asset;
import com.eams.entity.SensorData;
import com.eams.exception.AssetNotFoundException;
import com.eams.exception.SensorDataNotFoundException;
import com.eams.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataService {
    
    @Autowired
    private SensorDataRepository sensorDataRepository;
    
    @Autowired
    private AssetService assetService;
    
    // Create new sensor data
    public SensorDataResponseDTO createSensorData(SensorDataRequestDTO requestDTO) {
        Asset asset = assetService.getAssetEntityById(requestDTO.getAssetId());
        
        SensorData sensorData = new SensorData(
            asset,
            requestDTO.getSensorType(),
            requestDTO.getValue(),
            requestDTO.getUnit(),
            requestDTO.getThresholdMin(),
            requestDTO.getThresholdMax()
        );
        
        SensorData savedSensorData = sensorDataRepository.save(sensorData);
        return convertToResponseDTO(savedSensorData);
    }
    
    // Get sensor data by ID
    public SensorDataResponseDTO getSensorDataById(int id) {
        SensorData sensorData = sensorDataRepository.findById(id)
            .orElseThrow(() -> new SensorDataNotFoundException("Sensor data not found with ID: " + id));
        return convertToResponseDTO(sensorData);
    }
    
    // Get all sensor data
    public List<SensorDataResponseDTO> getAllSensorData() {
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data by asset ID
    public List<SensorDataResponseDTO> getSensorDataByAssetId(int assetId) {
        List<SensorData> sensorDataList = sensorDataRepository.findByAssetId(assetId);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data by sensor type
    public List<SensorDataResponseDTO> getSensorDataBySensorType(String sensorType) {
        List<SensorData> sensorDataList = sensorDataRepository.findBySensorType(sensorType);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data by asset ID and sensor type
    public List<SensorDataResponseDTO> getSensorDataByAssetIdAndSensorType(int assetId, String sensorType) {
        List<SensorData> sensorDataList = sensorDataRepository.findByAssetIdAndSensorType(assetId, sensorType);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data with alerts
    public List<SensorDataResponseDTO> getSensorDataWithAlerts() {
        List<SensorData> sensorDataList = sensorDataRepository.findByAlertTriggeredTrue();
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data with alerts for specific asset
    public List<SensorDataResponseDTO> getSensorDataWithAlertsByAssetId(int assetId) {
        List<SensorData> sensorDataList = sensorDataRepository.findByAssetIdAndAlertTriggeredTrue(assetId);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data within time range
    public List<SensorDataResponseDTO> getSensorDataByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<SensorData> sensorDataList = sensorDataRepository.findByTimestampBetween(startTime, endTime);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get sensor data for asset within time range
    public List<SensorDataResponseDTO> getSensorDataByAssetIdAndTimeRange(int assetId, LocalDateTime startTime, LocalDateTime endTime) {
        List<SensorData> sensorDataList = sensorDataRepository.findByAssetIdAndTimestampBetween(assetId, startTime, endTime);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get latest sensor data for each asset
    public List<SensorDataResponseDTO> getLatestSensorDataForEachAsset() {
        List<SensorData> sensorDataList = sensorDataRepository.findLatestSensorDataForEachAsset();
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get latest sensor data for specific asset
    public List<SensorDataResponseDTO> getLatestSensorDataForAsset(int assetId) {
        List<SensorData> sensorDataList = sensorDataRepository.findLatestSensorDataForAsset(assetId);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Update sensor data
    public SensorDataResponseDTO updateSensorData(int id, SensorDataRequestDTO requestDTO) {
        SensorData existingSensorData = sensorDataRepository.findById(id)
            .orElseThrow(() -> new SensorDataNotFoundException("Sensor data not found with ID: " + id));
        
        Asset asset = assetService.getAssetEntityById(requestDTO.getAssetId());
        
        existingSensorData.setAsset(asset);
        existingSensorData.setSensorType(requestDTO.getSensorType());
        existingSensorData.setValue(requestDTO.getValue());
        existingSensorData.setUnit(requestDTO.getUnit());
        existingSensorData.setThresholdMin(requestDTO.getThresholdMin());
        existingSensorData.setThresholdMax(requestDTO.getThresholdMax());
        
        SensorData updatedSensorData = sensorDataRepository.save(existingSensorData);
        return convertToResponseDTO(updatedSensorData);
    }
    
    // Delete sensor data
    public void deleteSensorData(int id) {
        if (!sensorDataRepository.existsById(id)) {
            throw new SensorDataNotFoundException("Sensor data not found with ID: " + id);
        }
        sensorDataRepository.deleteById(id);
    }
    
    // Get recent sensor data (last 24 hours)
    public List<SensorDataResponseDTO> getRecentSensorData() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        List<SensorData> sensorDataList = sensorDataRepository.findRecentSensorData(since);
        return sensorDataList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Convert entity to response DTO
    private SensorDataResponseDTO convertToResponseDTO(SensorData sensorData) {
        return new SensorDataResponseDTO(
            sensorData.getId(),
            sensorData.getAsset().getId(),
            sensorData.getAsset().getName(),
            sensorData.getSensorType(),
            sensorData.getValue(),
            sensorData.getUnit(),
            sensorData.getTimestamp(),
            sensorData.getThresholdMin(),
            sensorData.getThresholdMax(),
            sensorData.isAlertTriggered()
        );
    }
}
