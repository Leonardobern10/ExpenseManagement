package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.InsertDebtDTO;
import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.service.DebtsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentations/debt")
public class DebtController {

    private final DebtsService debtsService;

    public DebtController (DebtsService debtsService) {
        this.debtsService = debtsService;
    }

    @PostMapping // ? - TUDO OK!
    public ResponseEntity<Debt> createDebt (@RequestBody InsertDebtDTO debt) {
        return ResponseEntity.ok(debtsService.createDebt(debt));
    }

    @GetMapping // ? - TUDO OK!
    public ResponseEntity<List<Debt>> getAllDebts () {
        return ResponseEntity.ok(debtsService.getAllDebts());
    }

    @GetMapping("/{idDebt}") // ? - TUDO OK!
    public ResponseEntity<Debt> getOneDebt (@PathVariable String idDebt) {
        return ResponseEntity.ok(debtsService.getOneDebt(idDebt));
    }

    @GetMapping("/{idDebt}/payment/value") // ! - Não está funcionando
    public ResponseEntity<Void> updateStatusPayment(@PathVariable String idDebt,
                                                    @RequestParam Double value) {
        debtsService.updateStatusPayment(idDebt, value);
        return ResponseEntity.ok().build();
    }
}
