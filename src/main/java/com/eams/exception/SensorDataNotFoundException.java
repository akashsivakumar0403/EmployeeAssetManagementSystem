package com.eams.exception;

public class SensorDataNotFoundException extends RuntimeException {
    public SensorDataNotFoundException(String message) {
        super(message);
    }
}
