package com.eams.repository;

import com.eams.entity.SnoozedAlertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnoozedAlertTypeRepository extends JpaRepository<SnoozedAlertType, Long> {
    Optional<SnoozedAlertType> findByAlertTypeAndAssetId(String alertType, Long assetId);
}
