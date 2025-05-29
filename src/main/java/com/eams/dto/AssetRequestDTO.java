package com.eams.dto;

public class AssetRequestDTO {
    private String assetId;
    private String name;
    private String location;
    private String type;

    // Constructors
    public AssetRequestDTO() {}

    public AssetRequestDTO(String assetId, String name, String location, String type) {
        this.assetId = assetId;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    // Getters and Setters
    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
