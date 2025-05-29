package com.eams.repository;
import com.eams.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findBySensorNameAndStatus(String sensorName, String status);
}
