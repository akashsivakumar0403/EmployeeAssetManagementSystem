//package com.bridgelabz.EAMS.service.impl;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bridgelabz.EAMS.dto.SensorDataRequest;
//import com.bridgelabz.EAMS.dto.sensor.SensorDataResponse;
//import com.bridgelabz.EAMS.entity.Asset;
//import com.bridgelabz.EAMS.entity.SensorData;
//import com.bridgelabz.EAMS.repository.AssetRepository;
//import com.bridgelabz.EAMS.repository.SensorDataRepository;
//import com.bridgelabz.EAMS.service.SensorService;
//
//@Service
//public class SensorServiceImpl implements SensorService {
//
//    @Autowired
//    private SensorDataRepository sensorDataRepository;
//
//    @Autowired
//    private AssetRepository assetRepository;
//
//    public SensorDataResponse saveSensorData(SensorDataRequest request) {
//        Asset asset = assetRepository.findById(request.getAssetId())
//                .orElseThrow(() -> new RuntimeException("Asset not found"));
//
//        SensorData data = new SensorData();
//        data.setAsset(asset);
//        data.setTemperature(request.getTemperature());
//        data.setPressure(request.getPressure());
//        data.setTimestamp(LocalDateTime.now());
//
//        SensorData savedData = sensorDataRepository.save(data);
//
//        SensorDataResponse response = new SensorDataResponse();
//        response.setTemperature(savedData.getTemperature());
//        response.setPressure(savedData.getPressure());
//        response.setTimestamp(savedData.getTimestamp());
//        response.setAssetId(asset.getId());
//
//        return response;
//    }
//
//    public List<SensorDataResponse> getSensorDataByAssetId(Long assetId) {
//        return sensorDataRepository.findByAssetId(assetId).stream().map(data -> {
//            SensorDataResponse response = new SensorDataResponse();
//            response.setTemperature(data.getTemperature());
//            response.setPressure(data.getPressure());
//            response.setTimestamp(data.getTimestamp());
//            response.setAssetId(data.getAsset().getId());
//            return response;
//        }).collect(Collectors.toList());
//    }
//}
