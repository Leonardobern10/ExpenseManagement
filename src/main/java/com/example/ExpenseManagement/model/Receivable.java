package com.example.ExpenseManagement.model;

import com.example.ExpenseManagement.model.movimentations.Movimentations;

import java.time.LocalDateTime;

public class Receivable extends Movimentations {

    private StatusReceivable statusReceivable;

    public Receivable (String userId, String description, double amount,
                       String category, LocalDateTime dateTime,
                       StatusReceivable status, String person) {
        super(userId, description, amount, category, dateTime, person);
        this.statusReceivable = status;
    }

    public Receivable(){}

    public StatusReceivable getStatusReceivable() {
        return statusReceivable;
    }

    public void setStatusReceivable(StatusReceivable statusReceivable) {
        this.statusReceivable = statusReceivable;
    }

    public void receivedValue (double value) {
        if (value <= 0)
            throw new IllegalArgumentException("This value isn't permitted...");

        if (value > this.getAmount())
            throw new IllegalArgumentException("This operation isn't valid...");

        this.setAmount(this.getAmount() - value);

        if (this.getAmount() == 0)
            this.statusReceivable = StatusReceivable.RECEIVED;
    }
}
