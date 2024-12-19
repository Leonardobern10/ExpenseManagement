package com.example.ExpenseManagement.model;

public class Receivable extends Movimentations {
    private StatusReceivable statusReceivable;
    private String person;

    public void receivedValue (double value) {
        this.setAmount(this.getAmount() - value);
    }

    public StatusReceivable getStatusReceivable() {
        return statusReceivable;
    }

    public void setStatusReceivable(StatusReceivable statusReceivable) {
        this.statusReceivable = statusReceivable;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
