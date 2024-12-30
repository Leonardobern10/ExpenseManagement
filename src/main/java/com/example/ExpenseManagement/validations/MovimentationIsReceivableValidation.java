package com.example.ExpenseManagement.validations;

import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.model.Receivable;

public class MovimentationIsReceivableValidation {
    public static void validate (Movimentations movimentation) {
        if (! (movimentation.getClass() == Receivable.class) )
            throw new IllegalArgumentException("This debt is invalid!");
    }
}
