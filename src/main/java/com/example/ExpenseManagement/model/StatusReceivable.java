package com.example.ExpenseManagement.model;

public enum StatusReceivable {
    RECEIVED("received"),
    NOT_RECEIVED("not received");

    private final String description;

    StatusReceivable (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
