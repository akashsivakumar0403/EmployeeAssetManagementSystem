package com.eams.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MaintenanceEventRequest {

    @NotBlank(message = "Engineer name is required")
    private String engineerName;

    @NotBlank(message = "Notes cannot be empty")
    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;

    // Getters and setters
    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
