package com.eams.dto;

public class AssetRequestDTO {
    private String name;
    private String assetId;
    private String type;
    private String location;
    private String status;
    
    // Constructors
    public AssetRequestDTO() {}
    
    public AssetRequestDTO(String name, String assetId, String type, String location, String status) {
        this.name = name;
        this.assetId = assetId;
        this.type = type;
        this.location = location;
        this.status = status;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
