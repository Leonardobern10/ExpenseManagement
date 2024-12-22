package com.example.ExpenseManagement.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MovimentationUpdatedImpl implements MovimentationUpdater{
    private final Movimentations movimentations;

    public MovimentationUpdatedImpl (Movimentations movimentations) {
        this.movimentations = movimentations;
    }

    @Override
    public void updateDescription (String description) {
        movimentations.setDescription(description);
    }

    @Override
    public void updateAmount (double amount) {
        movimentations.setAmount(amount);
    }

    @Override
    public void updateCategory (String category) {
        movimentations.setCategory(category);
    }

    @Override
    public void updateDate (LocalDateTime dateTime) {
        movimentations.setDate(dateTime);
    }

    @Override
    public Movimentations updated () {
        return movimentations;
    }
}
