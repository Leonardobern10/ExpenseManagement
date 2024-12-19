package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    Page<Expense> findByUserId(String userId, Pageable pageable);
}
