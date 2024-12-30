package com.example.ExpenseManagement.validations;

import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.Receivable;
import com.example.ExpenseManagement.model.StatusDebt;
import com.example.ExpenseManagement.model.StatusReceivable;

public class StatusPaymentValidation {
    public static void validate (Double value, Double amount) {
        if ( value.isNaN() || value <= 0 || value > amount) {
            throw new IllegalArgumentException("This value is not permitted!");
        }
    }
    public static void validateNull (Receivable receivable) {
        if (receivable.getStatusReceivable() == null) {
            receivable.setStatusReceivable(StatusReceivable.NOT_RECEIVED);
        }
    }

    public static void validateNull (Debt debt) {
        if (debt.getStatusDebt() == null) {
            debt.setStatusDebt(StatusDebt.NOT_PAID_OFF);
        }
    }
}
