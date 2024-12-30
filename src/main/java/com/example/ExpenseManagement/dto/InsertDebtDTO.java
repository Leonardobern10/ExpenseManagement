package com.example.ExpenseManagement.dto;

import com.example.ExpenseManagement.model.StatusDebt;
import java.time.LocalDateTime;

public record InsertDebtDTO(String description, double amount,
                            String category, LocalDateTime dateTime,
                            StatusDebt statusDebt, String person) {};
