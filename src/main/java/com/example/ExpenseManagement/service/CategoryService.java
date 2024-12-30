package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AlertLimitUltrapassedEmailDTO;
import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.model.user.User;
import org.springframework.stereotype.Service;

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
}
