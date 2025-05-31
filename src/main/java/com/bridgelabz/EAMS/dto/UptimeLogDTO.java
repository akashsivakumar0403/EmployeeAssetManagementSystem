package com.bridgelabz.EAMS.dto;

import java.time.LocalDateTime;

public class UptimeLogDTO {
    private Long assetId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
