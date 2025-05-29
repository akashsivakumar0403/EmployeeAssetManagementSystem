package com.eams.repository;

import com.eams.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    Optional<Asset> findByAssetId(String assetId);
    boolean existsByAssetId(String assetId);
}
