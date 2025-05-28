package com.eams.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "asset_id", nullable = false, unique = true)
    private String assetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column
    private String type;

    // Constructors
    public Asset() {}

    public Asset(String assetId, String name, String location, String type) {
        this.assetId = assetId;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
