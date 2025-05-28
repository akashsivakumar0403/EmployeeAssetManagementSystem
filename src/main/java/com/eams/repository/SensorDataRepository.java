package com.eams.repository;

import com.eams.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {}
