package com.example.ExpenseManagement.model.user;

import com.example.ExpenseManagement.model.Role;

public class UserDirector {
    public static User construct (UserBuilder builder, String username,
                                  String password, Role role, String email) {
        builder.buildUsername(username);
        builder.buildPassword(password);
        builder.buildRole(role);
        builder.buildEmail(email);
        return builder.buildUser();
    }
}
