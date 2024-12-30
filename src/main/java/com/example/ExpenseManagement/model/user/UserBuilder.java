package com.example.ExpenseManagement.model.user;

import com.example.ExpenseManagement.model.Role;

public interface UserBuilder {
    public void buildUsername(String username);
    public void buildPassword(String password);
    public void buildRole(Role role);
    public void buildEmail(String email);
    public User buildUser();
}
