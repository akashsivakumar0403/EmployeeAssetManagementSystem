package com.bridgelabz.EAMS.exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String role) {
        super("Invalid role: " + role + ". Allowed values: MANAGER, OPERATOR, ADMIN.");
    }
}