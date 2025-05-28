package com.eams.service;

import com.eams.dto.AssetRequestDTO;
import com.eams.dto.AssetResponseDTO;
import com.eams.entity.Asset;
import com.eams.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    // Add New Asset
    public AssetResponseDTO addAsset(AssetRequestDTO dto) {
        if(assetRepository.existsByAssetId(dto.getAssetId())) {
            throw new RuntimeException("Asset ID already exists.");
        }
        Asset asset = new Asset(dto.getAssetId(), dto.getName(), dto.getLocation(), dto.getType());
        Asset saved = assetRepository.save(asset);
        return toDTO(saved);
    }

    // Edit Asset Details
    public AssetResponseDTO updateAsset(int id, AssetRequestDTO dto) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setName(dto.getName());
        asset.setLocation(dto.getLocation());
        asset.setType(dto.getType());
        assetRepository.save(asset);
        return toDTO(asset);
    }

    // View All Assets
    public List<AssetResponseDTO> getAllAssets() {
        return assetRepository.findAll()
                .stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Delete Inactive Asset
    public void deleteAsset(int id) {
        if(!assetRepository.existsById(id)) {
            throw new RuntimeException("Asset not found");
        }
        assetRepository.deleteById(id);
    }

    // Helper
    private AssetResponseDTO toDTO(Asset asset) {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setId(asset.getId());
        dto.setAssetId(asset.getAssetId());
        dto.setName(asset.getName());
        dto.setLocation(asset.getLocation());
        dto.setType(asset.getType());
        return dto;
    }
}
