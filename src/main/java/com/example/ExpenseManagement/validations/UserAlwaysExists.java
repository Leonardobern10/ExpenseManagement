package com.example.ExpenseManagement.validations;

import com.example.ExpenseManagement.model.User;

import java.util.Optional;

public class UserAlwaysExists {

    public static void validate (Optional<User> user) {
        if (user.isPresent())
            throw new IllegalArgumentException("Username always exists!");
    }
}
