package com.eams.service;

import com.eams.dto.AssetRequestDTO;
import com.eams.dto.AssetResponseDTO;
import com.eams.entity.Asset;
import com.eams.exception.AssetNotFoundException;
import com.eams.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {
    
    @Autowired
    private AssetRepository assetRepository;
    
    // Create new asset
    public AssetResponseDTO createAsset(AssetRequestDTO requestDTO) {
        if (assetRepository.existsByAssetId(requestDTO.getAssetId())) {
            throw new IllegalArgumentException("Asset ID already exists: " + requestDTO.getAssetId());
        }
        
        Asset asset = new Asset(
            requestDTO.getName(),
            requestDTO.getAssetId(),
            requestDTO.getType(),
            requestDTO.getLocation(),
            requestDTO.getStatus()
        );
        
        Asset savedAsset = assetRepository.save(asset);
        return convertToResponseDTO(savedAsset);
    }
    
    // Get asset by ID
    public AssetResponseDTO getAssetById(int id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new AssetNotFoundException("Asset not found with ID: " + id));
        return convertToResponseDTO(asset);
    }
    
    // Get asset by asset ID
    public AssetResponseDTO getAssetByAssetId(String assetId) {
        Asset asset = assetRepository.findByAssetId(assetId)
            .orElseThrow(() -> new AssetNotFoundException("Asset not found with Asset ID: " + assetId));
        return convertToResponseDTO(asset);
    }
    
    // Get all assets
    public List<AssetResponseDTO> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get assets by type
    public List<AssetResponseDTO> getAssetsByType(String type) {
        List<Asset> assets = assetRepository.findByType(type);
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get assets by location
    public List<AssetResponseDTO> getAssetsByLocation(String location) {
        List<Asset> assets = assetRepository.findByLocation(location);
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get assets by status
    public List<AssetResponseDTO> getAssetsByStatus(String status) {
        List<Asset> assets = assetRepository.findByStatus(status);
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Search assets by name
    public List<AssetResponseDTO> searchAssetsByName(String name) {
        List<Asset> assets = assetRepository.findByNameContainingIgnoreCase(name);
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Update asset
    public AssetResponseDTO updateAsset(int id, AssetRequestDTO requestDTO) {
        Asset existingAsset = assetRepository.findById(id)
            .orElseThrow(() -> new AssetNotFoundException("Asset not found with ID: " + id));
        
        // Check if asset ID is being changed and if it already exists
        if (!existingAsset.getAssetId().equals(requestDTO.getAssetId()) &&
            assetRepository.existsByAssetId(requestDTO.getAssetId())) {
            throw new IllegalArgumentException("Asset ID already exists: " + requestDTO.getAssetId());
        }
        
        existingAsset.setName(requestDTO.getName());
        existingAsset.setAssetId(requestDTO.getAssetId());
        existingAsset.setType(requestDTO.getType());
        existingAsset.setLocation(requestDTO.getLocation());
        existingAsset.setStatus(requestDTO.getStatus());
        
        Asset updatedAsset = assetRepository.save(existingAsset);
        return convertToResponseDTO(updatedAsset);
    }
    
    // Delete asset
    public void deleteAsset(int id) {
        if (!assetRepository.existsById(id)) {
            throw new AssetNotFoundException("Asset not found with ID: " + id);
        }
        assetRepository.deleteById(id);
    }
    
    // Get active assets
    public List<AssetResponseDTO> getActiveAssets() {
        List<Asset> assets = assetRepository.findActiveAssets();
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Get assets in maintenance
    public List<AssetResponseDTO> getAssetsInMaintenance() {
        List<Asset> assets = assetRepository.findAssetsInMaintenance();
        return assets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Convert entity to response DTO
    private AssetResponseDTO convertToResponseDTO(Asset asset) {
        return new AssetResponseDTO(
            asset.getId(),
            asset.getName(),
            asset.getAssetId(),
            asset.getType(),
            asset.getLocation(),
            asset.getStatus(),
            asset.getCreatedAt(),
            asset.getUpdatedAt()
        );
    }
    
    // Helper method to get Asset entity (for use by other services)
    public Asset getAssetEntityById(int id) {
        return assetRepository.findById(id)
            .orElseThrow(() -> new AssetNotFoundException("Asset not found with ID: " + id));
    }
}
