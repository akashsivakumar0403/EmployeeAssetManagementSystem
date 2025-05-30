package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.AssetDTO;
import java.util.List;

public interface AssetService {
    AssetDTO createAsset(AssetDTO assetDTO);
    AssetDTO updateAsset(Long id, AssetDTO assetDTO);
    List<AssetDTO> getAllAssets();
    void deleteAsset(Long id);
}
