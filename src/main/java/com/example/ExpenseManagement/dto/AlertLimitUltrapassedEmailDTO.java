package com.example.ExpenseManagement.dto;

import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.model.user.User;

public record AlertLimitUltrapassedEmailDTO(Category category, User user) {
}
