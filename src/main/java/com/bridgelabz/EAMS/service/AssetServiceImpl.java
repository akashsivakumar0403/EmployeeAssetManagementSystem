package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.AssetDTO;
import com.bridgelabz.EAMS.entity.Asset;
import com.bridgelabz.EAMS.entity.User;
import com.bridgelabz.EAMS.repository.AssetRepository;
import com.bridgelabz.EAMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AssetDTO createAsset(AssetDTO dto) {
        Asset asset = mapToEntity(dto);
        Asset saved = assetRepository.save(asset);
        return mapToDTO(saved);
    }

    @Override
    public AssetDTO updateAsset(Long id, AssetDTO dto) {
        Asset existing = assetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Asset not found with id " + id));

        existing.setName(dto.getName());
        existing.setType(dto.getType());
        existing.setLocation(dto.getLocation());
        existing.setThresholdTemp(dto.getThresholdTemp());
        existing.setThresholdPressure(dto.getThresholdPressure());

        if (dto.getAssignedToUserId() != null) {
            User user = userRepository.findById(dto.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getAssignedToUserId()));
            existing.setAssignedTo(user);
        }

        Asset updated = assetRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public List<AssetDTO> getAllAssets() {
        return assetRepository.findAll()
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }

    // --- Mapping Helpers ---
    private Asset mapToEntity(AssetDTO dto) {
        Asset asset = new Asset();
        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());

        if (dto.getAssignedToUserId() != null) {
            User user = userRepository.findById(dto.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getAssignedToUserId()));
            asset.setAssignedTo(user);
        }
        return asset;
    }

    private AssetDTO mapToDTO(Asset asset) {
        AssetDTO dto = new AssetDTO();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setType(asset.getType());
        dto.setLocation(asset.getLocation());
        dto.setThresholdTemp(asset.getThresholdTemp());
        dto.setThresholdPressure(asset.getThresholdPressure());
        dto.setAssignedToUserId(
            asset.getAssignedTo() != null
                ? asset.getAssignedTo().getId()
                : null
        );
        return dto;
    }
}
