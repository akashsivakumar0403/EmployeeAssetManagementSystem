package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.*;
import com.bridgelabz.EAMS.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@Tag(name = "Maintenance & Uptime Logs", description = "Log maintenance and generate uptime reports")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/log-maintenance")
    @Operation(summary = "Record a scheduled/completed maintenance log")
    public ResponseEntity<String> logMaintenance(@RequestBody MaintenanceLogDTO dto) {
        maintenanceService.addMaintenanceLog(dto);
        return ResponseEntity.ok("Maintenance log recorded.");
    }

    @PostMapping("/log-uptime")
    @Operation(summary = "Log uptime or downtime period")
    public ResponseEntity<String> logUptime(@RequestBody UptimeLogDTO dto) {
        maintenanceService.addUptimeLog(dto);
        return ResponseEntity.ok("Uptime log recorded.");
    }

    @GetMapping("/report")
    @Operation(summary = "Generate uptime/downtime report")
    public ResponseEntity<List<UptimeReportDTO>> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(maintenanceService.getReport(start, end));
    }
}
