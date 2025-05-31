package com.bridgelabz.EAMS.repository;

import com.bridgelabz.EAMS.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    List<MaintenanceLog> findByScheduledDateBetween(LocalDate start, LocalDate end);
}
