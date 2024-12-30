package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.UpdateMovimentationDTO;
import com.example.ExpenseManagement.model.Month;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.service.MovimentationsService;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentations")
public class MovimentationsController {

    private final MovimentationsService movimentationsService;

    public MovimentationsController (MovimentationsService movimentationsService) {
        this.movimentationsService = movimentationsService;
    }

    // ? - Tudo ok!
    @GetMapping
    public ResponseEntity<PagedModel<Movimentations>> getExpenses (
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PagedModel<Movimentations> expensesPage = movimentationsService.getMovimentationsByUser(page, size);
        return ResponseEntity.ok(expensesPage);
    }

    // ? - Tudo ok!
    @GetMapping("/{expenseId}")
    public Movimentations getOneExpense (@PathVariable String expenseId) {
        return movimentationsService.getOneMovimentation(expenseId);
    }

    // ? - Tudo ok!
    @DeleteMapping("/{id}") // TUDO OK
    public ResponseEntity<Void> deleteMovimentation (@PathVariable String id) {
        movimentationsService.deleteMovimentation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idExpense}")
    public ResponseEntity<Void> updateMovimentation (
            @PathVariable String idExpense,
            @RequestBody UpdateMovimentationDTO movimentationDTO) {
        movimentationsService.updateMovimentation(idExpense, movimentationDTO);
        return ResponseEntity.ok().build();
    }

    // ? - Tudo ok!
    @GetMapping("/category")
    public ResponseEntity<List<Movimentations>> getMovimentationsByCategory (
            @RequestParam String category) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByCategory(category));
    }

    // ? - Tudo ok!
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movimentations>> getMovimentationsByYear (
            @PathVariable Integer year) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByYear(year));
    }

    // ? - Tudo ok!
    @GetMapping("/year/{year}/month")
    public ResponseEntity<List<Movimentations>> getMovimentationsByMonth (
            @PathVariable Integer year, @RequestParam Month month) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByMonth(year, month));
    }

    // ? - Tudo ok!
    @GetMapping("/year/{year}/month/{month}/day")
    public ResponseEntity<List<Movimentations>> getMovimentationsByDay (
            @PathVariable Integer year, @PathVariable Month month,
            @RequestParam Integer day) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByDay(year, month, day));
    }
}

