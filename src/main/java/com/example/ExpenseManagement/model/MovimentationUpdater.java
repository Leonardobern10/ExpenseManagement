package com.example.ExpenseManagement.model;

import java.time.LocalDateTime;

public interface MovimentationUpdater {
    public void updateDescription( String description );
    public void updateAmount( double amount );
    public void updateCategory( String category );
    public void updateDate(LocalDateTime dateTime);
    public Movimentations updated();
}
