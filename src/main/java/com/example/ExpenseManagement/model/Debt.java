package com.example.ExpenseManagement.model;

import java.time.LocalDateTime;

public class Debt extends Movimentations {

    private StatusDebt statusDebt;

    public Debt (String userId, String description, double amount,
                       String category, LocalDateTime dateTime,
                       StatusDebt statusDebt, String person) {

        super(userId, description, amount, category, dateTime, person);
        this.statusDebt = statusDebt;
    }

    public Debt(){};

    public StatusDebt getStatusDebt() {
        return statusDebt;
    }

    public void setStatusDebt(StatusDebt statusDebt) {
        this.statusDebt = statusDebt;
    }

    public void paidOffValue (double value) {
        if (value <= 0)
            throw new IllegalArgumentException("This value isn't permitted...");

        if (value > this.getAmount())
            throw new IllegalArgumentException("This operation isn't valid...");

        this.setAmount(this.getAmount() - value);

        if (this.getAmount() == 0)
            this.statusDebt = StatusDebt.PAID_OFF;
    }
}
