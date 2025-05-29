package com.eams.repository;
import com.eams.entity.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ThresholdRepository extends JpaRepository<Threshold, Long> {
    Optional<Threshold> findBySensorName(String sensorName);
}
