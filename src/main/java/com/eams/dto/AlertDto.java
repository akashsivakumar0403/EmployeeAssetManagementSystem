package com.eams.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertDto {

    private Long id;  // optional for create, required for update

    @NotBlank(message = "Sensor name is required")
    private String sensorName;

    @NotNull(message = "Sensor value is required")
    private Double sensorValue;  // renamed for clarity

    @NotBlank(message = "Alert type is required")
    private String alertType;

    private Boolean active = true;

    private String message;

    // Optionally, add timestamps:
    // private LocalDateTime createdAt;
    // private LocalDateTime resolvedAt;
}
