package com.bridgelabz.EAMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.EAMS.entity.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findByAssetId(Long assetId);
}