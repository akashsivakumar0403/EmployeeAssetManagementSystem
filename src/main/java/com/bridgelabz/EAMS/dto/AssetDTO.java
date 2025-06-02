package com.bridgelabz.EAMS.dto;

import com.bridgelabz.EAMS.entity.User;

import jakarta.persistence.*;

public class AssetDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String location;

    private Double thresholdTemp;
    private Double thresholdPressure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignedTo;

	private Long assignedToUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getThresholdTemp() {
        return thresholdTemp;
    }

    public void setThresholdTemp(Double thresholdTemp) {
        this.thresholdTemp = thresholdTemp;
    }

    public Double getThresholdPressure() {
        return thresholdPressure;
    }

    public void setThresholdPressure(Double thresholdPressure) {
        this.thresholdPressure = thresholdPressure;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }
    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }
}
