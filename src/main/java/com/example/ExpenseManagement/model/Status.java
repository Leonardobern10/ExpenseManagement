package com.example.ExpenseManagement.model;

public enum Status {
    PAID_OFF("Paid off"),
    NOT_PAID_OFF("Not paid off");

    private final String description;

    Status (String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }
}
