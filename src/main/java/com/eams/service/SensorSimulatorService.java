package com.eams.service;

import com.eams.entity.Alert;
import com.eams.entity.SensorData;
import com.eams.repository.AlertRepository;
import com.eams.repository.SensorDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SensorSimulatorService {

    private final SensorDataRepository sensorDataRepository;
    private final AlertRepository alertRepository;
    private final Random random = new Random();

    @Autowired
    public SensorSimulatorService(SensorDataRepository sensorDataRepository, AlertRepository alertRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.alertRepository = alertRepository;
    }

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void simulateSensorReading() {
        String[] assets = {"Compressor_1", "Pump_2", "Valve_3"};
        String asset = assets[random.nextInt(assets.length)];

        double temperature = 60 + (100 - 60) * random.nextDouble();
        double pressure = 1 + (5 - 1) * random.nextDouble();
        LocalDateTime now = LocalDateTime.now();

        SensorData data = new SensorData(asset, temperature, pressure, now);
        sensorDataRepository.save(data);

        if (temperature > 85) {
            Alert alert = new Alert(asset, "High temperature", "TEMPERATURE", temperature, now);
            alertRepository.save(alert);
            System.out.println("⚠️ High temperature alert for " + asset);
        }

        if (pressure > 4.5) {
            Alert alert = new Alert(asset, "High pressure", "PRESSURE", pressure, now);
            alertRepository.save(alert);
            System.out.println("⚠️ High pressure alert for " + asset);
        }

        System.out.println("✅ Simulated sensor data for " + asset);
    }
}

