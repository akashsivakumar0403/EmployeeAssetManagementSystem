package com.bridgelabz.EAMS.dto;

public class UptimeReportDTO {
    private Long assetId;
    private long totalUptimeMinutes;
    private long totalDowntimeMinutes;

    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }

    public long getTotalUptimeMinutes() { return totalUptimeMinutes; }
    public void setTotalUptimeMinutes(long totalUptimeMinutes) { this.totalUptimeMinutes = totalUptimeMinutes; }

    public long getTotalDowntimeMinutes() { return totalDowntimeMinutes; }
    public void setTotalDowntimeMinutes(long totalDowntimeMinutes) { this.totalDowntimeMinutes = totalDowntimeMinutes; }
}

