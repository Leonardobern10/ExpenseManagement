package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.InsertReceivableDTO;
import com.example.ExpenseManagement.model.*;
import com.example.ExpenseManagement.repository.MovimentationsRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceivableServices {
    private final MovimentationsRepository movimentationsRepository;
    private final UserSearchService search;

    ReceivableServices (MovimentationsRepository movimentationsRepository,
                        UserSearchService search) {
        this.movimentationsRepository = movimentationsRepository;
        this.search = search;
    }

    public Receivable createReceivable (InsertReceivableDTO receivable) {
        try {
            String userId = search.searchUsername();
            Receivable newReceivable = new Receivable(userId, receivable.description(), receivable.amount(),
                    receivable.category(), receivable.dateTime(), receivable.statusReceivable(), receivable.person());// Associa a despesa ao usuário

            if (receivable.dateTime() == null) {
                newReceivable.setDate(LocalDateTime.now());
            }

            if (receivable.statusReceivable() == null) {
                newReceivable.setStatusReceivable(StatusReceivable.NOT_RECEIVED);
            }

            return movimentationsRepository.save(newReceivable);
        } catch ( JwtException e) {
            System.out.println(e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }

    public List<Receivable> getAllReceivables () {
        try {
            List<Movimentations> movimentations = movimentationsRepository.findAll();
            List<Receivable> receivables = new ArrayList<>(List.of());
            for (Movimentations mov : movimentations) {
                if (mov.getClass() == Receivable.class) {
                    receivables.add((Receivable) mov);
                }
            }
            return receivables;
        } catch ( JwtException | IllegalArgumentException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Receivable getOneReceivables (String idDebt) {
        Movimentations movimentation = movimentationsRepository.findById(idDebt)
                .orElseThrow(() -> new IllegalArgumentException("Debt register is invalid!"));

        if (! (movimentation.getClass() == Receivable.class) )
            throw new IllegalArgumentException("This debt is invalid!");

        return (Receivable) movimentation;
    }

    public void updateStatusReceivement (String idDebt, Double value) {
        Receivable receivable = getOneReceivables(idDebt);

        if ( value.isNaN() || value <= 0 || value > receivable.getAmount()){
            throw new IllegalArgumentException("This value is invalid!");
        }

        receivable.setAmount(receivable.getAmount() - value);

        if ( receivable.getAmount() == 0) {
            receivable.setStatusReceivable(StatusReceivable.RECEIVED);
        }

        ValuePaid valuePaid = new ValuePaid(LocalDateTime.now(), value);

        if (receivable.getRegisters() == null) {
            receivable.setRegisters(new ArrayList<>());
        }
        receivable.addRegister(valuePaid.toString());
        movimentationsRepository.save(receivable);
    }
}
