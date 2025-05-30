package com.bridgelabz.EAMS.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User roles")
public enum UserRole {
    MANAGER,
    OPERATOR,
    TECHNICIAN
}

