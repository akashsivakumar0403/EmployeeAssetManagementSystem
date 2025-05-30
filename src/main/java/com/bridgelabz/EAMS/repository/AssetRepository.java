package com.bridgelabz.EAMS.repository;

import com.bridgelabz.EAMS.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    // no additional methods needed for basic CRUD
}
