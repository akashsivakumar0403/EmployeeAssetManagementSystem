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
    
    // ✅ Correct: POST to base path for creation
    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(@RequestBody AssetRequestDTO requestDTO) {
        AssetResponseDTO responseDTO = assetService.createAsset(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // ✅ Correct: Path variable for integer ID
    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable int id) {
        AssetResponseDTO responseDTO = assetService.getAssetById(id);
        return ResponseEntity.ok(responseDTO);
    }

    // ... (other methods remain valid)
}
