package com.group32.bookservice.model;

import lombok.Getter;

@Getter
public enum RoleEnum {
    USER("Default user role"),
    ADMIN("Administrator role"),
    SUPER_ADMIN("Super Administrator role");


    private final String description;

    RoleEnum(String s) {
        this.description = s;
    }
}
