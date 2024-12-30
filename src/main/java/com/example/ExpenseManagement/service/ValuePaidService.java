package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.model.Receivable;
import com.example.ExpenseManagement.model.ValuePaid;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ValuePaidService {
    public static void insertValuePaid (Receivable receivable, Double value) {
        ValuePaid valuePaid = new ValuePaid(LocalDateTime.now(), value);
        if (receivable.getRegisters() == null) {
            receivable.setRegisters(new ArrayList<>());
        }
        receivable.addRegister(valuePaid.toString());
    }
    public static void insertValuePaid (Debt debt, Double value) {
        ValuePaid valuePaid = new ValuePaid(LocalDateTime.now(), value);
        if (debt.getRegisters() == null) {
            debt.setRegisters(new ArrayList<>());
        }
        debt.addRegister(valuePaid.toString());
    }
    public static void insertValuePaid (Movimentations movimentations, Double value) {
        ValuePaid valuePaid = new ValuePaid(LocalDateTime.now(), value);
        if (movimentations.getRegisters() == null) {
            movimentations.setRegisters(new ArrayList<>());
        }
        movimentations.addRegister(valuePaid.toString());
    }
}
