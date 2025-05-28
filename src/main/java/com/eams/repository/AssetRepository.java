package com.eams.repository;

import com.eams.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    
    // Find asset by unique asset ID
    Optional<Asset> findByAssetId(String assetId);
    
    // Check if asset exists by asset ID
    boolean existsByAssetId(String assetId);
    
    // Find assets by type
    List<Asset> findByType(String type);
    
    // Find assets by location
    List<Asset> findByLocation(String location);
    
    // Find assets by status
    List<Asset> findByStatus(String status);
    
    // Find assets by name containing (case-insensitive search)
    List<Asset> findByNameContainingIgnoreCase(String name);
    
    // Find assets by type and status
    List<Asset> findByTypeAndStatus(String type, String status);
    
    // Find assets by location and status
    List<Asset> findByLocationAndStatus(String location, String status);
    
    // Custom query to find active assets
    @Query("SELECT a FROM Asset a WHERE a.status = 'ACTIVE'")
    List<Asset> findActiveAssets();
    
    // Custom query to find assets in maintenance
    @Query("SELECT a FROM Asset a WHERE a.status = 'MAINTENANCE'")
    List<Asset> findAssetsInMaintenance();
    
    // Custom query to count assets by status
    @Query("SELECT COUNT(a) FROM Asset a WHERE a.status = :status")
    long countByStatus(@Param("status") String status);
}
