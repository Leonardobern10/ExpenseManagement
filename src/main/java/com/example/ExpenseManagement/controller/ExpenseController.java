package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.CreateExpenseDTO;
import com.example.ExpenseManagement.dto.UpdateExpenseDTO;
import com.example.ExpenseManagement.model.Expense;
import com.example.ExpenseManagement.model.Month;
import com.example.ExpenseManagement.service.ExpenseService;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<PagedModel<Expense>> getExpenses(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PagedModel<Expense> expensesPage = expenseService.getExpensesByUser(page, size);
        return ResponseEntity.ok(expensesPage);
    }

    @GetMapping("/{expenseId}")
    public Expense getOneExpense (@PathVariable String expenseId) {
        return expenseService.getOneExpense(expenseId);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody CreateExpenseDTO expense) {
        return ResponseEntity.ok(expenseService.createExpense(expense));
    }

    /*PostMapping("/addAll")
    public ResponseEntity<Void> createExpense(@RequestBody List<CreateExpenseDTO> expenses) {
        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idExpense}")
    public ResponseEntity<Void> updateExpense(@PathVariable String idExpense, @RequestBody UpdateExpenseDTO expense) {
        expenseService.updateExpense(idExpense, expense);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment/{idExpense}")
    public ResponseEntity<Void> updateSituationPayment(@PathVariable String idExpense) {
        expenseService.updateSituation(idExpense);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<Expense>> getExpensesCategory(@RequestParam String category) {
        return ResponseEntity.ok(expenseService.getExpensesCategory(category));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Expense>> getExpensesByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(expenseService.getExpensesByYear(year));
    }

    @GetMapping("/year/{year}/month")
    public ResponseEntity<List<Expense>> getExpensesByMonth(@PathVariable Integer year, @RequestParam Month month) {
        return ResponseEntity.ok(expenseService.getExpensesByMonth(year, month));
    }

    @GetMapping("/year/{year}/month/{month}/day")
    public ResponseEntity<List<Expense>> getExpensesByMonth(@PathVariable Integer year, @PathVariable Integer month, @RequestParam Integer day) {
        return ResponseEntity.ok(expenseService.getExpensesByDay(year, month, day));
    }
}
