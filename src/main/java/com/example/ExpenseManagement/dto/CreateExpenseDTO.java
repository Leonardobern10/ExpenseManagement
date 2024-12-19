package com.example.ExpenseManagement.dto;

import java.time.LocalDateTime;

public record CreateExpenseDTO (String description, double amount, String category, LocalDateTime date) {
}
