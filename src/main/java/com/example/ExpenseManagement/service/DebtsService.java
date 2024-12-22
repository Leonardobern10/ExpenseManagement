package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.InsertDebtDTO;
import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.Movimentations;
import com.example.ExpenseManagement.model.StatusDebt;
import com.example.ExpenseManagement.model.ValuePaid;
import com.example.ExpenseManagement.repository.MovimentationsRepository;
import com.example.ExpenseManagement.repository.MovimentationsRepositoryCustomImpl;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DebtsService {

    private final MovimentationsRepository movimentationsRepository;
    private final UserSearchService search;
    private final MovimentationsRepositoryCustomImpl movimentationsRepositoryCustom;

    public DebtsService (MovimentationsRepository movimentationsRepository,
                         UserSearchService search,
                         MovimentationsRepositoryCustomImpl movimentationsRepositoryCustom) {
        this.movimentationsRepository = movimentationsRepository;
        this.search = search;
        this.movimentationsRepositoryCustom = movimentationsRepositoryCustom;
    }

    public List<Debt> getAllDebts () {
        try {
            List<Movimentations> movimentations = movimentationsRepository.findAll();
            List<Debt> debts = new ArrayList<>(List.of());
            for (Movimentations mov : movimentations) {
                if (mov.getClass() == Debt.class) {
                    debts.add((Debt) mov);
                }
            }
            return debts;
        } catch ( JwtException | IllegalArgumentException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Debt getOneDebt (String idDebt) {
        Movimentations movimentation = movimentationsRepository.findById(idDebt)
                .orElseThrow(() -> new IllegalArgumentException("Debt register is invalid!"));

        if (! (movimentation.getClass() == Debt.class) )
            throw new IllegalArgumentException("This debt is invalid!");

        return (Debt) movimentation;
    }

    public Debt createDebt(InsertDebtDTO debt) {
        try {
            String userId = search.searchUsername();
            Debt newDebt = new Debt(userId, debt.description(), debt.amount(),
                    debt.category(), debt.dateTime(), debt.statusDebt(), debt.person());// Associa a despesa ao usuário

            if (debt.dateTime() == null) {
                newDebt.setDate(LocalDateTime.now());
            }

            if (debt.statusDebt() == null) {
                newDebt.setStatusDebt(StatusDebt.NOT_PAID_OFF);
            }

            return movimentationsRepository.save(newDebt);
        } catch ( RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateStatusPayment (String idDebt, Double value) {
        Debt debt = getOneDebt(idDebt);

        if ( value.isNaN() || value <= 0 || value > debt.getAmount()) {
            throw new IllegalArgumentException("This value is not permitted!");
        }

        debt.setAmount(debt.getAmount() - value);

        if ( debt.getAmount() == 0 ) {
            debt.setStatusDebt(StatusDebt.PAID_OFF);
        }

        ValuePaid valuePaid = new ValuePaid(LocalDateTime.now(), value);
        if (debt.getRegisters() == null) {
            debt.setRegisters(new ArrayList<>());
        }
        debt.addRegister(valuePaid.toString());
        movimentationsRepository.save(debt);
    }

    public void limitPerCategory (String category, Double limit) {
    }
}
