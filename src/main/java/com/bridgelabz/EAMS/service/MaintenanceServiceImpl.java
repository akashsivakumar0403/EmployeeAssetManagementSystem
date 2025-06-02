package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.*;
import com.bridgelabz.EAMS.entity.*;
import com.bridgelabz.EAMS.repository.*;
import com.bridgelabz.EAMS.service.IMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {

    @Autowired
    private MaintenanceLogRepository maintenanceRepo;

    @Autowired
    private UptimeLogRepository uptimeRepo;

    @Override
    public void addMaintenanceLog(MaintenanceLogDTO dto) {
        MaintenanceLog log = new MaintenanceLog();
        log.setAssetId(dto.getAssetId());
        log.setScheduledDate(dto.getScheduledDate());
        log.setCompletedDate(dto.getCompletedDate());
        log.setRemarks(dto.getRemarks());
        maintenanceRepo.save(log);
    }

    @Override
    public void addUptimeLog(UptimeLogDTO dto) {
        UptimeLog log = new UptimeLog();
        log.setAssetId(dto.getAssetId());
        log.setStartTime(dto.getStartTime());
        log.setEndTime(dto.getEndTime());
        log.setStatus(dto.getStatus());
        uptimeRepo.save(log);
    }

    @Override
    public List<UptimeReportDTO> getReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<UptimeLog> logs = uptimeRepo.findByStartTimeBetween(start, end);
        Map<Long, UptimeReportDTO> reportMap = new HashMap<>();

        for (UptimeLog log : logs) {
            UptimeReportDTO report = reportMap.getOrDefault(log.getAssetId(), new UptimeReportDTO());
            report.setAssetId(log.getAssetId());

            long minutes = Duration.between(log.getStartTime(), log.getEndTime()).toMinutes();
            if ("UP".equalsIgnoreCase(log.getStatus())) {
                report.setTotalUptimeMinutes(report.getTotalUptimeMinutes() + minutes);
            } else {
                report.setTotalDowntimeMinutes(report.getTotalDowntimeMinutes() + minutes);
            }

            reportMap.put(log.getAssetId(), report);
        }

        return new ArrayList<>(reportMap.values());
    }
}
