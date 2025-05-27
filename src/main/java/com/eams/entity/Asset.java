package com.eams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assets")
public class Asset {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String assetId;
    
    @Column(nullable = false)
    private String type; // MACHINE, EQUIPMENT, etc.
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE, MAINTENANCE
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorData> sensorDataList;
    
    // Constructors
    public Asset() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Asset(String name, String assetId, String type, String location, String status) {
        this();
        this.name = name;
        this.assetId = assetId;
        this.type = type;
        this.location = location;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { 
        this.assetId = assetId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getType() { return type; }
    public void setType(String type) { 
        this.type = type;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { 
        this.location = location;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<SensorData> getSensorDataList() { return sensorDataList; }
    public void setSensorDataList(List<SensorData> sensorDataList) { this.sensorDataList = sensorDataList; }
    
    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assetId='" + assetId + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
