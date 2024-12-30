package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.movimentations.Movimentations;

import java.util.List;

public interface MovimentationsRepositoryCustom {
    List<Movimentations> findMovimentationsByYear (String userId, int year);

    List<Movimentations> findMovimentationsByMonth (String userId, int year,
                                                    int month);

    List<Movimentations> findMovimentationsByDay (String userId, int year,
                                                  int month, int day);

    List<Movimentations> findMovimentationsByCategory(String userId, String category);
}
