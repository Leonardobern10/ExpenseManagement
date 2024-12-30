package com.example.ExpenseManagement.validations;

import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.Receivable;

import java.time.LocalDateTime;

public class DateTimeValidation {
    public static void validate (Debt debt) {
        if (debt.getDate() == null) {
            debt.setDate(LocalDateTime.now());
        }
    }
    public static void validate (Receivable receivable) {
        if (receivable.getDate() == null) {
            receivable.setDate(LocalDateTime.now());
        }
    }
}
