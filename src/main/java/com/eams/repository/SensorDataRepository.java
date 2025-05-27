package com.eams.repository;

import com.eams.entity.SensorData;
import com.eams.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    
    // Find all sensor data for a specific asset
    List<SensorData> findByAsset(Asset asset);
    
    // Find sensor data by asset ID
    List<SensorData> findByAssetId(int assetId);
    
    // Find sensor data by sensor type
    List<SensorData> findBySensorType(String sensorType);
    
    // Find sensor data by asset and sensor type
    List<SensorData> findByAssetAndSensorType(Asset asset, String sensorType);
    
    // Find sensor data by asset ID and sensor type
    List<SensorData> findByAssetIdAndSensorType(int assetId, String sensorType);
    
    // Find sensor data with alerts triggered
    List<SensorData> findByAlertTriggeredTrue();
    
    // Find sensor data with alerts triggered for specific asset
    List<SensorData> findByAssetAndAlertTriggeredTrue(Asset asset);
    
    // Find sensor data by asset ID with alerts triggered
    List<SensorData> findByAssetIdAndAlertTriggeredTrue(int assetId);
    
    // Find sensor data within a time range
    List<SensorData> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    // Find sensor data for asset within time range
    List<SensorData> findByAssetAndTimestampBetween(Asset asset, LocalDateTime startTime, LocalDateTime endTime);
    
    // Find sensor data by asset ID within time range
    List<SensorData> findByAssetIdAndTimestampBetween(int assetId, LocalDateTime startTime, LocalDateTime endTime);
    
    // Custom query to find latest sensor data for each asset
    @Query("SELECT s FROM SensorData s WHERE s.timestamp = " +
           "(SELECT MAX(s2.timestamp) FROM SensorData s2 WHERE s2.asset = s.asset AND s2.sensorType = s.sensorType)")
    List<SensorData> findLatestSensorDataForEachAsset();
    
    // Custom query to find latest sensor data for specific asset
    @Query("SELECT s FROM SensorData s WHERE s.asset.id = :assetId AND s.timestamp = " +
           "(SELECT MAX(s2.timestamp) FROM SensorData s2 WHERE s2.asset.id = :assetId AND s2.sensorType = s.sensorType)")
    List<SensorData> findLatestSensorDataForAsset(@Param("assetId") int assetId);
    
    // Custom query to find sensor data with values outside threshold
    @Query("SELECT s FROM SensorData s WHERE s.value < s.thresholdMin OR s.value > s.thresholdMax")
    List<SensorData> findSensorDataOutsideThreshold();
    
    // Custom query to count alerts by asset
    @Query("SELECT COUNT(s) FROM SensorData s WHERE s.asset.id = :assetId AND s.alertTriggered = true")
    long countAlertsByAsset(@Param("assetId") int assetId);
    
    // Custom query to find recent sensor data (last 24 hours)
    @Query("SELECT s FROM SensorData s WHERE s.timestamp >= :since ORDER BY s.timestamp DESC")
    List<SensorData> findRecentSensorData(@Param("since") LocalDateTime since);
}
