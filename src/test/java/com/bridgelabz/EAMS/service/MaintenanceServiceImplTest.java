package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.MaintenanceLogDTO;
import com.bridgelabz.EAMS.dto.UptimeLogDTO;
import com.bridgelabz.EAMS.dto.UptimeReportDTO;
import com.bridgelabz.EAMS.entity.MaintenanceLog;
import com.bridgelabz.EAMS.entity.UptimeLog;
import com.bridgelabz.EAMS.repository.MaintenanceLogRepository;
import com.bridgelabz.EAMS.repository.UptimeLogRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MaintenanceServiceImplTest {

    @InjectMocks
    private MaintenanceServiceImpl maintenanceService;

    @Mock
    private MaintenanceLogRepository maintenanceRepo;

    @Mock
    private UptimeLogRepository uptimeRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMaintenanceLog() {
        MaintenanceLogDTO dto = new MaintenanceLogDTO();
        dto.setAssetId(1L);
        dto.setScheduledDate(LocalDate.now());
        dto.setCompletedDate(LocalDate.now().plusDays(1));
        dto.setRemarks("Routine Check");

        maintenanceService.addMaintenanceLog(dto);

        ArgumentCaptor<MaintenanceLog> captor = ArgumentCaptor.forClass(MaintenanceLog.class);
        verify(maintenanceRepo, times(1)).save(captor.capture());

        MaintenanceLog savedLog = captor.getValue();
        assertEquals(dto.getAssetId(), savedLog.getAssetId());
        assertEquals(dto.getScheduledDate(), savedLog.getScheduledDate());
        assertEquals(dto.getCompletedDate(), savedLog.getCompletedDate());
        assertEquals(dto.getRemarks(), savedLog.getRemarks());
    }

    @Test
    void testAddUptimeLog() {
        UptimeLogDTO dto = new UptimeLogDTO();
        dto.setAssetId(1L);
        dto.setStartTime(LocalDateTime.now().minusHours(1));
        dto.setEndTime(LocalDateTime.now());
        dto.setStatus("UP");

        maintenanceService.addUptimeLog(dto);

        ArgumentCaptor<UptimeLog> captor = ArgumentCaptor.forClass(UptimeLog.class);
        verify(uptimeRepo, times(1)).save(captor.capture());

        UptimeLog savedLog = captor.getValue();
        assertEquals(dto.getAssetId(), savedLog.getAssetId());
        assertEquals(dto.getStartTime(), savedLog.getStartTime());
        assertEquals(dto.getEndTime(), savedLog.getEndTime());
        assertEquals(dto.getStatus(), savedLog.getStatus());
    }

    @Test
    void testGetReport() {
        Long assetId = 1L;
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        List<UptimeLog> logs = new ArrayList<>();

        UptimeLog upLog = new UptimeLog();
        upLog.setAssetId(assetId);
        upLog.setStartTime(startTime);
        upLog.setEndTime(startTime.plusHours(1));
        upLog.setStatus("UP");

        UptimeLog downLog = new UptimeLog();
        downLog.setAssetId(assetId);
        downLog.setStartTime(startTime.plusHours(2));
        downLog.setEndTime(startTime.plusHours(3));
        downLog.setStatus("DOWN");

        logs.add(upLog);
        logs.add(downLog);

        when(uptimeRepo.findByStartTimeBetween(any(), any())).thenReturn(logs);

        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = endTime.toLocalDate();

        List<UptimeReportDTO> reports = maintenanceService.getReport(startDate, endDate);

        assertEquals(1, reports.size());
        UptimeReportDTO report = reports.get(0);
        assertEquals(assetId, report.getAssetId());
        assertEquals(60, report.getTotalUptimeMinutes());
        assertEquals(60, report.getTotalDowntimeMinutes());
    }
}
