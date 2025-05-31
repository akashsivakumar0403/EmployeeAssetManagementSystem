package com.bridgelabz.EAMS.repository;

import com.bridgelabz.EAMS.entity.UptimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UptimeLogRepository extends JpaRepository<UptimeLog, Long> {
    List<UptimeLog> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
