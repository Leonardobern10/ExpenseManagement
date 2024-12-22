package com.example.ExpenseManagement.model;

import org.springframework.stereotype.Component;

@Component
public class UserBuilderImpl implements UserBuilder {

    User user = null;

    public UserBuilderImpl () {
        this.user = new User();
    }

    @Override
    public void buildUsername (String username) {
        user.setUsername(username);
    }

    @Override
    public void buildPassword (String password) {
        user.setPassword(password);
    }

    @Override
    public void buildRole (Role role) {
        user.setRole(role);
    }

    @Override
    public User buildUser () {
        return user;
    }
}
