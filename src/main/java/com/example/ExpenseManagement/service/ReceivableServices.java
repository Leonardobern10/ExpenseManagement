package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.InsertReceivableDTO;
import com.example.ExpenseManagement.model.*;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.repository.MovimentationsRepository;
import com.example.ExpenseManagement.validations.DateTimeValidation;
import com.example.ExpenseManagement.validations.MovimentationIsReceivableValidation;
import com.example.ExpenseManagement.validations.PaymentValidation;
import com.example.ExpenseManagement.validations.StatusPaymentValidation;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

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
            String userId = search.searchUsername().getId();
            Receivable newReceivable = new Receivable(
                    userId, receivable.description(), receivable.amount(),
                    receivable.category(), receivable.dateTime(),
                    receivable.statusReceivable(), receivable.person());// Associa a despesa ao usuário

            DateTimeValidation.validate(newReceivable);

            StatusPaymentValidation.validateNull(newReceivable);

            return movimentationsRepository.save(newReceivable);
        } catch ( JwtException e) {
            System.out.println(e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }

    public List<Receivable> getAllReceivables () {
        try {
            List<Movimentations> movimentations = movimentationsRepository.findAll();
            return toIdentifyReceivables(movimentations);
        } catch ( JwtException | IllegalArgumentException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Receivable getOneReceivables (String idDebt) {
        try {
            Movimentations movimentation = movimentationsRepository.findById(idDebt)
                    .orElseThrow(() -> new IllegalArgumentException("Debt register is invalid!"));

            MovimentationIsReceivableValidation.validate(movimentation);

            return (Receivable) movimentation;
        } catch ( RuntimeException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateStatusReceivement (String idDebt, Double value) {
        Receivable receivable = getOneReceivables(idDebt);
        StatusPaymentValidation.validate(value, receivable.getAmount());
        receivable.setAmount(receivable.getAmount() - value);
        PaymentValidation.validate(receivable);
        ValuePaidService.insertValuePaid(receivable, value);
        movimentationsRepository.save(receivable);
    }

    private List<Receivable> toIdentifyReceivables (List<Movimentations> movimentations) {
        List<Receivable> receivables = new ArrayList<>(List.of());
        for (Movimentations mov : movimentations) {
            if (mov.getClass() == Receivable.class) {
                receivables.add((Receivable) mov);
            }
        }
        return receivables;
    }
}
