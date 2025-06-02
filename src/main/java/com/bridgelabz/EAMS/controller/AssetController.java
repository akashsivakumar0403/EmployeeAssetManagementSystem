package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.AssetDTO;
import com.bridgelabz.EAMS.service.IAssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@Tag(name = "Assets", description = "Asset Management APIs")
public class AssetController {

    @Autowired
    private IAssetService assetService;

    @PostMapping
    @Operation(summary = "Add a new asset")
    public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO) {
        AssetDTO created = assetService.createAsset(assetDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all assets")
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing asset")
    public ResponseEntity<AssetDTO> updateAsset(
            @PathVariable Long id,
            @RequestBody AssetDTO assetDTO) {
        AssetDTO updated = assetService.updateAsset(id, assetDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an asset by ID")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}