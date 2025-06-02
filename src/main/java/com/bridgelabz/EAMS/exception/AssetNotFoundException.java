package com.bridgelabz.EAMS.exception;


public class AssetNotFoundException extends RuntimeException {
    public AssetNotFoundException(String assetName) {
        super("Asset not found: " + assetName);
    }
}