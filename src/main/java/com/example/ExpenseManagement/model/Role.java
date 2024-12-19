package com.example.ExpenseManagement.model;

public enum Role {
    USER ("USER"),
    ADMIN ("ADMIN");

    private final String description;

    Role (String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }
}
