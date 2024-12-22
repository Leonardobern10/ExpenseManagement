package com.example.ExpenseManagement.model;

public enum StatusDebt {
    PAID_OFF("paid off"),
    NOT_PAID_OFF("not paid off");

    private final String description;

    StatusDebt (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
