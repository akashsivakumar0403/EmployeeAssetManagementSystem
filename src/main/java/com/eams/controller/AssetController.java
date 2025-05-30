package com.eams.controller;

import com.eams.dto.AssetRequestDTO;
import com.eams.dto.AssetResponseDTO;
import com.eams.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; 

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // Add New Asset
    @PostMapping
    public ResponseEntity<AssetResponseDTO> addAsset(@Valid @RequestBody AssetRequestDTO dto) {
        return ResponseEntity.ok(assetService.addAsset(dto));
    }

    // Edit Asset Details
    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(@PathVariable int id, @Valid @RequestBody AssetRequestDTO dto) {
        return ResponseEntity.ok(assetService.updateAsset(id, dto));
    }

    // View All Assets
    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    // Delete Inactive Asset
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable int id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok("Asset deleted successfully.");
    }
}
