package com.example.ExpenseManagement.model;

import java.time.LocalDateTime;

public class ValuePaid {
    private LocalDateTime dateTime;
    private Double value;

    public ValuePaid (LocalDateTime dateTime, Double value) {
        this.value = value;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime () {
        return dateTime;
    }

    public void setDateTime (LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getValue () {
        return value;
    }

    public void setValue (Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Data de recebimento: %s | Valor: R$ %.2f",
                dateTime.toString(), value);
    }
}
