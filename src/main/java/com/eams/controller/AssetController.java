package com.eams.controller;

import com.eams.dto.AssetRequestDTO;
import com.eams.dto.AssetResponseDTO;
import com.eams.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    // Create new asset
    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(@RequestBody AssetRequestDTO requestDTO) {
        AssetResponseDTO responseDTO = assetService.createAsset(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    // Get asset by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable int id) {
        AssetResponseDTO responseDTO = assetService.getAssetById(id);
        return ResponseEntity.ok(responseDTO);
    }
    
    // Get asset by asset ID
    @GetMapping("/assetId/{assetId}")
    public ResponseEntity<AssetResponseDTO> getAssetByAssetId(@PathVariable String assetId) {
        AssetResponseDTO responseDTO = assetService.getAssetByAssetId(assetId);
        return ResponseEntity.ok(responseDTO);
    }
    
    // Get all assets
    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> getAllAssets() {
        List<AssetResponseDTO> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }
    
    // Get assets by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsByType(@PathVariable String type) {
        List<AssetResponseDTO> assets = assetService.getAssetsByType(type);
        return ResponseEntity.ok(assets);
    }
    
    // Get assets by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsByLocation(@PathVariable String location) {
        List<AssetResponseDTO> assets = assetService.getAssetsByLocation(location);
        return ResponseEntity.ok(assets);
    }
    
    // Get assets by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsByStatus(@PathVariable String status) {
        List<AssetResponseDTO> assets = assetService.getAssetsByStatus(status);
        return ResponseEntity.ok(assets);
    }
    
    // Search assets by name
    @GetMapping("/search")
    public ResponseEntity<List<AssetResponseDTO>> searchAssetsByName(@RequestParam String name) {
        List<AssetResponseDTO> assets = assetService.searchAssetsByName(name);
        return ResponseEntity.ok(assets);
    }
    
    // Get active assets
    @GetMapping("/active")
    public ResponseEntity<List<AssetResponseDTO>> getActiveAssets() {
        List<AssetResponseDTO> assets = assetService.getActiveAssets();
        return ResponseEntity.ok(assets);
    }
    
    // Get assets in maintenance
    @GetMapping("/maintenance")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsInMaintenance() {
        List<AssetResponseDTO> assets = assetService.getAssetsInMaintenance();
        return ResponseEntity.ok(assets);
    }
    
    // Update asset
    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(@PathVariable int id, 
                                                       @RequestBody AssetRequestDTO requestDTO) {
        AssetResponseDTO responseDTO = assetService.updateAsset(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    
    // Delete asset
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable int id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok("Asset deleted successfully");
    }
}
