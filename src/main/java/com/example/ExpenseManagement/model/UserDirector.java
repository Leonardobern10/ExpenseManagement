package com.example.ExpenseManagement.model;

public class UserDirector {
    public static User construct (UserBuilder builder, String username,
                                  String password, Role role) {
        builder.buildUsername(username);
        builder.buildPassword(password);
        builder.buildRole(role);
        return builder.buildUser();
    }
}
