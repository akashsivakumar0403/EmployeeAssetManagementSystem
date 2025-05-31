package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {
    void addMaintenanceLog(MaintenanceLogDTO dto);
    void addUptimeLog(UptimeLogDTO dto);
    List<UptimeReportDTO> getReport(LocalDate startDate, LocalDate endDate);
}