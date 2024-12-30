package com.example.ExpenseManagement.model;

import com.example.ExpenseManagement.model.movimentations.Movimentations;

import java.time.LocalDateTime;

public class Debt extends Movimentations {

    private StatusDebt statusDebt;

    public Debt (String userId, String description, double amount,
                 String categoryName, LocalDateTime dateTime,
                 StatusDebt statusDebt, String person) {
        super(userId, description, amount, categoryName, dateTime, person);
        this.statusDebt = statusDebt;
    }

    public Debt(){};

    public StatusDebt getStatusDebt() {
        return statusDebt;
    }

    public void setStatusDebt(StatusDebt statusDebt) {
        this.statusDebt = statusDebt;
    }
}
