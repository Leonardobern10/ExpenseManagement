package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseRepositoryCustom {
    List<Expense> findExpensesByYear (String userId, int year);

    List<Expense> findExpensesByMonth (String userId, int year, int month);

    List<Expense> findExpensesByDay (String userId, int year, int month, int day);

    List<Expense> findExpenseByCategory(String userId, String category);
}
