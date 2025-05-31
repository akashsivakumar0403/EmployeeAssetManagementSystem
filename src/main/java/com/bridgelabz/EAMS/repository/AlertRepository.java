package com.bridgelabz.EAMS.repository;

import com.bridgelabz.EAMS.entity.Alert;
import com.bridgelabz.EAMS.entity.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByStatus(AlertStatus status);
}
