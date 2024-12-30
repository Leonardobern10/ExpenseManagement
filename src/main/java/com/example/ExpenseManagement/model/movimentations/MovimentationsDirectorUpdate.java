package com.example.ExpenseManagement.model.movimentations;

import java.time.LocalDateTime;

public class MovimentationsDirectorUpdate {
    public static Movimentations construct (MovimentationUpdater movimentationUpdater,
                                            String description, double amount,
                                            String category, LocalDateTime dateTime) {
        movimentationUpdater.updateDescription(description);
        movimentationUpdater.updateAmount(amount);
        movimentationUpdater.updateCategory(category);
        movimentationUpdater.updateDate(dateTime);
        return movimentationUpdater.updated();
    }
}
