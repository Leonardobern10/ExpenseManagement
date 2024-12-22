package com.example.ExpenseManagement.dto;

import com.example.ExpenseManagement.model.StatusReceivable;

import java.time.LocalDateTime;

public record InsertReceivableDTO(String description, double amount,
                                  String category, LocalDateTime dateTime,
                                  StatusReceivable statusReceivable, String person) {
}
