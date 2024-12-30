package com.example.ExpenseManagement.validations;

import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.Receivable;
import com.example.ExpenseManagement.model.StatusDebt;
import com.example.ExpenseManagement.model.StatusReceivable;

public class PaymentValidation {
    public static void validate (Receivable receivable) {
        if ( receivable.getAmount() == 0) {
            receivable.setStatusReceivable(StatusReceivable.RECEIVED);
        }
    }

    public static void validate (Debt debt) {
        if ( debt.getAmount() == 0 ) {
            debt.setStatusDebt(StatusDebt.PAID_OFF);
        }
    }
}
