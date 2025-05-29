package com.eams.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AssetRequestDTO {

    @NotBlank(message = "Asset ID is required")
    @Pattern(regexp = "^[A-Z0-9]{4,10}$", message = "Asset ID must be 4-10 uppercase letters or digits")
    private String assetId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be 3-50 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Name must be alphanumeric and spaces only")
    private String name;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 50, message = "Location must be 2-50 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ,.-]+$", message = "Location can contain letters, numbers, spaces, commas, periods, and hyphens")
    private String location;

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "^(ELECTRONIC|FURNITURE|VEHICLE|OTHER)$", message = "Type must be one of: ELECTRONIC, FURNITURE, VEHICLE, OTHER")
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
	