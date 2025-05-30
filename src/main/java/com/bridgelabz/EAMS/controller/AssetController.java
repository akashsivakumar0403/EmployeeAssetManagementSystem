package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.AssetDTO;
import com.bridgelabz.EAMS.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // 1) Add New Asset
    @PostMapping
    public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO) {
        AssetDTO created = assetService.createAsset(assetDTO);
        return ResponseEntity.ok(created);
    }

    // 3) View All Assets
    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    // 2) Edit Asset Details
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(
            @PathVariable Long id,
            @RequestBody AssetDTO assetDTO) {
        AssetDTO updated = assetService.updateAsset(id, assetDTO);
        return ResponseEntity.ok(updated);
    }

    // 4) Delete Inactive Assets
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}
