package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.InsertReceivableDTO;
import com.example.ExpenseManagement.model.Movimentations;
import com.example.ExpenseManagement.model.Receivable;
import com.example.ExpenseManagement.service.ReceivableServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentations/receivable")
public class ReceivableController {

    private final ReceivableServices receivableServices;

    public ReceivableController (ReceivableServices receivableServices) {
        this.receivableServices = receivableServices;
    }

    @PostMapping() // ? - TUDO OK!
    public ResponseEntity<Movimentations> createReceivable (
            @RequestBody InsertReceivableDTO receivable) {
        return ResponseEntity.ok(receivableServices.createReceivable(receivable));
    }

    @GetMapping // ? - TUDO OK!
    public ResponseEntity<List<Receivable>> getAllDebts () {
        return ResponseEntity.ok(receivableServices.getAllReceivables());
    }

    @GetMapping("/{idDebt}") // ? - TUDO OK!
    public ResponseEntity<Receivable> getOneDebt (
            @PathVariable String idDebt) {
        return ResponseEntity.ok(receivableServices.getOneReceivables(idDebt));
    }

    @GetMapping("/{idDebt}/payment/value") // ? - TUDO OK!
    public ResponseEntity<Void> updateStatusPayment(
            @PathVariable String idDebt,
            @RequestParam Double value) {
        receivableServices.updateStatusReceivement(idDebt, value);
        return ResponseEntity.ok().build();
    }
}
