package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.CreateExpenseDTO;
import com.example.ExpenseManagement.dto.UpdateExpenseDTO;
import com.example.ExpenseManagement.model.Expense;
import com.example.ExpenseManagement.model.Month;
import com.example.ExpenseManagement.model.Status;
import com.example.ExpenseManagement.repository.ExpenseRepository;
import com.example.ExpenseManagement.repository.ExpenseRepositoryCustom;
import com.example.ExpenseManagement.repository.ExpenseRepositoryCustomImpl;
import com.example.ExpenseManagement.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseRepositoryCustom expenseRepositoryCustom;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository, ExpenseRepositoryCustomImpl expenseRepositoryCustom) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expenseRepositoryCustom = expenseRepositoryCustom;
    }

    public PagedModel<Expense> getExpensesByUser(Integer page, Integer size) {
        try {
            String userId = searchUsername();
            Pageable pageable = PageRequest.of(page, size);
            return new PagedModel<>(expenseRepository.findByUserId(userId, pageable));
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Expense getOneExpense (String id) {
        try {
        searchUsername();
        return expenseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("This expense don't exists!"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Expense createExpense(CreateExpenseDTO expense) {
        try {
            String userId = searchUsername();
            Expense newExpense = new Expense(expense.description(), expense.amount(), expense.category(), expense.date());
            newExpense.setUserId(userId); // Associa a despesa ao usuário
            if (expense.date() == null) {
                newExpense.setDate(LocalDateTime.now());
            }
            return expenseRepository.save(newExpense);
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            throw new JwtException(e.getMessage());
        }
    }

 /*

! - @DECPRECATED
 public void createMultipleExpenses(List<CreateExpenseDTO> expenses) {
        expenses.stream().map(exp -> {
            try {
                return createExpense(exp);
            } catch (RuntimeException e) {
                System.out.println("Erro ao criar despesa: " + e.getMessage());
                // Se necessário, adicione lógica para registrar a falha e retornar algo
                return null;  // Retorne null ou outro valor indicativo de falha
            }
        }).collect(Collectors.toList());
    }
*/

    public void deleteExpense(String expenseId) {
        try {
            searchUsername();
            expenseRepository.deleteById(expenseId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void updateExpense(String idExpense, UpdateExpenseDTO expense) {
        try {
        searchUsername();
        Expense oldExpense = getOneExpense(idExpense);
        oldExpense.setDescription(expense.description());
        oldExpense.setAmount(expense.amount());
        oldExpense.setCategory(expense.category());
        oldExpense.setDate(LocalDateTime.now());
        expenseRepository.save(oldExpense);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void updateSituation (String idExpense) {
        try {
            searchUsername();
            Expense oldExpense = getOneExpense(idExpense);
            if (oldExpense.getStatus() == Status.NOT_PAID_OFF) {
                oldExpense.setStatus(Status.PAID_OFF);
            } else {
                oldExpense.setStatus(Status.NOT_PAID_OFF);
            }
            expenseRepository.save(oldExpense);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Expense> getExpensesCategory (String category) {
        try {
            String userId = searchUsername();
            System.out.println(userId);
            return expenseRepositoryCustom.findExpenseByCategory(userId, category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Expense> getExpensesByYear (int year) {
        try {
            String userId = searchUsername();
            return expenseRepositoryCustom.findExpensesByYear(userId, year);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Expense> getExpensesByMonth (int year, Month month) {
        try {
            String userId = searchUsername();
            return expenseRepositoryCustom.findExpensesByMonth(userId, year, month.getDescription());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Expense> getExpensesByDay (int year, int month, int day) {
        try {
            String userId = searchUsername();
            return expenseRepositoryCustom.findExpensesByDay(userId, year, month, day);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String searchUsername () throws IllegalArgumentException {
        // Obtém o nome de usuário a partir do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // O nome de usuário está no contexto de autenticação

        // Busca o ID do usuário correspondente
        return String.valueOf(userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("User not found!")
        ).getId());
    }
}
