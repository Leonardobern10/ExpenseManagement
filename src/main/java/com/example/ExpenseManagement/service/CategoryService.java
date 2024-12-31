package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AlertLimitUltrapassedEmailDTO;
import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class CategoryService {

    private final EmailService emailService;

    CategoryService (EmailService emailService) {
        this.emailService = emailService;
    }

    public void amountBiggerLimit (User user, Category category) {
        System.out.println("amountBiggerLimit() foi chamada ");
        try {
            if ( category.getLimit() != 0 && category.getLimit() <= category.getTotalValue() ) {
                emailService
                        .sendSimpleEmail(new AlertLimitUltrapassedEmailDTO(category, user));
            }
        } catch ( RuntimeException e ) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createCategory (User user, Debt debt) {
        // Procura uma categoria dentro de uma li
        Category category = searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null) {
            category.setTotalValue(category.getTotalValue() + debt.getAmount());
            return false; // Categoria atualizada - false
        }
        user.getCategories().add(new Category(debt.getCategoryName(), debt.getAmount()));
        return true; // Categoria criada - true
    }

    public Category searchCategory (Set<Category> categories, String categoryName) {
        if ( categories.isEmpty() )
            return null;

        for ( Category category : categories ) {
            if ( Objects.equals(category.getName(), categoryName) ) {
                return category;
            }
        }
        return null;
    }
}
